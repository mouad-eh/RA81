import java.io.*;
import java.net.*;
import java.util.*;

public class RicartAgrawalaMutex {
    private static final int TOTAL_PROCESSES = 10;
    private static final int BASE_PORT = 5000;

    private int processId;
    private int[] deferredReplies;
    private int outstandingReplies;
    private boolean accessingCriticalSection;
    private Random random;
    private int osn;
    private int hsn;
    private Object lock; // For mutual exclusion

    public RicartAgrawalaMutex(int processId) {
        this.processId = processId;
        this.deferredReplies = new int[TOTAL_PROCESSES];
        this.outstandingReplies = 0;
        this.accessingCriticalSection = false;
        this.random = new Random();
        this.osn = 0;
        this.hsn = 0;
        this.lock = new Object();
    }

    public void start() {
        new Thread(new ServerThread()).start();
        new Thread(new ClientThread()).start();
    }

    private class ServerThread implements Runnable {
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(BASE_PORT + processId);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(new ServerWorker(clientSocket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ServerWorker implements Runnable {
        private Socket clientSocket;

        public ServerWorker(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String messageType = in.readLine();

                if (messageType.equals("REQUEST")) {
                    int requestingProcessId = Integer.parseInt(in.readLine());
                    int requestingClockValue = Integer.parseInt(in.readLine());

                    // System.out.println(
                    // "[Process " + processId + "] Received request from process " +
                    // requestingProcessId);

                    synchronized (lock) {
                        hsn = Math.max(requestingClockValue, hsn) + 1;

                        if (accessingCriticalSection && (requestingClockValue > osn
                                || (requestingClockValue == osn && processId < requestingProcessId))) {
                            // System.out.print(
                            // "deffer the request from " + requestingProcessId + " by " + processId +
                            // "\n");
                            deferredReplies[requestingProcessId] = 1;
                        } else {
                            Socket replySocket = new Socket(clientSocket.getInetAddress(),
                                    BASE_PORT + requestingProcessId);
                            PrintWriter out = new PrintWriter(replySocket.getOutputStream(), true);
                            out.println("REPLY");
                            out.println(processId);
                            replySocket.close();
                        }
                    }
                } else if (messageType.equals("REPLY")) {
                    int replyingProcessId = Integer.parseInt(in.readLine());

                    // System.out.println("[Process " + processId + "] Received reply from process "
                    // + replyingProcessId);

                    synchronized (lock) {
                        outstandingReplies--;
                        // System.out.println("*****" + processId + "*****" + outstandingReplies);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientThread implements Runnable {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(random.nextInt(4000) + 1000); // Random delay between 1 and 5 seconds

                    synchronized (lock) {
                        osn = hsn;
                        accessingCriticalSection = true;
                        outstandingReplies = TOTAL_PROCESSES - 1;
                    }

                    System.out.println("[Process " + processId + "] Requesting critical section with osn = " + osn);

                    for (int i = 0; i < TOTAL_PROCESSES; i++) {
                        if (i != processId) {
                            Socket requestSocket = new Socket(InetAddress.getLocalHost(), BASE_PORT + i);
                            PrintWriter out = new PrintWriter(requestSocket.getOutputStream(), true);
                            out.println("REQUEST");
                            out.println(processId);
                            out.println(osn);
                            requestSocket.close();
                        }
                    }
                    while (true) {
                        synchronized (lock) {
                            if (outstandingReplies == 0) {
                                break;
                            }
                        }
                        // Wait for replies from other processes
                    }

                    System.out.println("[Process " + processId + "] Entering critical section");
                    Thread.sleep(random.nextInt(1000) + 1000); // Critical section execution
                    System.out.println("[Process " + processId + "] Exiting critical section");

                    synchronized (lock) {
                        accessingCriticalSection = false;

                        for (int i = 0; i < TOTAL_PROCESSES; i++) {
                            if (deferredReplies[i] == 1) {
                                Socket replySocket = new Socket(InetAddress.getLocalHost(), BASE_PORT + i);
                                PrintWriter out = new PrintWriter(replySocket.getOutputStream(), true);
                                out.println("REPLY");
                                out.println(processId);
                                replySocket.close();
                                deferredReplies[i] = 0;
                                // System.out.println("sending a late reply from " + processId + " to " + i +
                                // "\n");
                            }
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < TOTAL_PROCESSES; i++) {
            RicartAgrawalaMutex process = new RicartAgrawalaMutex(i);
            process.start();
        }
    }
}