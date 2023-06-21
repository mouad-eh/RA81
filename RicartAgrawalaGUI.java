// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import java.awt.*;
// import java.util.ArrayList;
// import java.util.List;

// public class RicartAgrawalaGUI extends JFrame {
//     private List<ProcessPanel> processPanels;

//     public RicartAgrawalaGUI() {
//         processPanels = new ArrayList<>();

//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setTitle("Ricart-Agrawala Mutex");

//         JPanel mainPanel = new JPanel();
//         mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//         mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

//         // Create process panels
//         for (int i = 0; i < 10; i++) {
//             ProcessPanel processPanel = new ProcessPanel(i);
//             processPanels.add(processPanel);
//             mainPanel.add(processPanel);
//         }

//         JScrollPane scrollPane = new JScrollPane(mainPanel);
//         add(scrollPane);

//         pack();
//         setLocationRelativeTo(null);
//         setVisible(true);
//     }

//     public void updateProcessInfo(int processId, int osn, int hsn, boolean inCriticalSection) {
//         ProcessPanel processPanel = processPanels.get(processId);
//         processPanel.updateInfo(osn, hsn, inCriticalSection);
//     }

//     public void addLogEntry(int processId, String logEntry) {
//         ProcessPanel processPanel = processPanels.get(processId);
//         processPanel.addLogEntry(logEntry);
//     }

//     private static class ProcessPanel extends JPanel {
//         private JLabel processIdLabel;
//         private JLabel osnLabel;
//         private JLabel hsnLabel;
//         private JLabel inCriticalSectionLabel;
//         private JTextArea logTextArea;

//         public ProcessPanel(int processId) {
//             setLayout(new BorderLayout());
//             setBorder(BorderFactory.createEtchedBorder());

//             processIdLabel = new JLabel("Process ID: " + processId);
//             osnLabel = new JLabel("OSN: ");
//             hsnLabel = new JLabel("HSN: ");
//             inCriticalSectionLabel = new JLabel("In Critical Section: ");

//             JPanel infoPanel = new JPanel(new GridLayout(4, 1));
//             infoPanel.add(processIdLabel);
//             infoPanel.add(osnLabel);
//             infoPanel.add(hsnLabel);
//             infoPanel.add(inCriticalSectionLabel);

//             logTextArea = new JTextArea();
//             logTextArea.setEditable(false);

//             JScrollPane scrollPane = new JScrollPane(logTextArea);

//             add(infoPanel, BorderLayout.CENTER);
//             add(scrollPane, BorderLayout.SOUTH);
//         }

//         public void updateInfo(int osn, int hsn, boolean inCriticalSection) {
//             osnLabel.setText("OSN: " + osn);
//             hsnLabel.setText("HSN: " + hsn);
//             inCriticalSectionLabel.setText("In Critical Section: " + inCriticalSection);
//         }

//         public void addLogEntry(String logEntry) {
//             logTextArea.append(logEntry + "\n");
//         }
//     }
// }

//************************************ */

// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import java.awt.*;
// import java.util.HashMap;
// import java.util.Map;

// public class RicartAgrawalaGUI extends JFrame {
//     private Map<Integer, ProcessPanel> processPanels;

//     public RicartAgrawalaGUI() {
//         processPanels = new HashMap<>();

//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setTitle("Ricart-Agrawala Mutex");

//         JPanel mainPanel = new JPanel();
//         mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//         mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

//         for (int i = 0; i < 10; i++) {
//             ProcessPanel processPanel = new ProcessPanel(i);
//             processPanels.put(i, processPanel);
//             mainPanel.add(processPanel);
//         }

//         JScrollPane scrollPane = new JScrollPane(mainPanel);
//         add(scrollPane);

//         pack();
//         setLocationRelativeTo(null);
//         setVisible(true);
//     }

//     public void updateProcessInfo(int processId, int osn, int hsn, boolean inCriticalSection) {
//         SwingUtilities.invokeLater(() -> {
//             ProcessPanel processPanel = processPanels.get(processId);
//             processPanel.updateInfo(osn, hsn, inCriticalSection);
//         });
//     }

//     public void addLogEntry(int processId, String logEntry) {
//         SwingUtilities.invokeLater(() -> {
//             ProcessPanel processPanel = processPanels.get(processId);
//             processPanel.addLogEntry(logEntry);
//         });
//     }

//     private static class ProcessPanel extends JPanel {
//         private JLabel processIdLabel;
//         private JLabel osnLabel;
//         private JLabel hsnLabel;
//         private JLabel inCriticalSectionLabel;
//         private JTextArea logTextArea;

//         public ProcessPanel(int processId) {
//             setLayout(new BorderLayout());
//             setBorder(BorderFactory.createEtchedBorder());

//             processIdLabel = new JLabel("Process ID: " + processId);
//             osnLabel = new JLabel("OSN: ");
//             hsnLabel = new JLabel("HSN: ");
//             inCriticalSectionLabel = new JLabel("In Critical Section: ");

//             JPanel infoPanel = new JPanel(new GridLayout(4, 1));
//             infoPanel.add(processIdLabel);
//             infoPanel.add(osnLabel);
//             infoPanel.add(hsnLabel);
//             infoPanel.add(inCriticalSectionLabel);

//             logTextArea = new JTextArea();
//             logTextArea.setEditable(false);

//             JScrollPane scrollPane = new JScrollPane(logTextArea);

//             add(infoPanel, BorderLayout.CENTER);
//             add(scrollPane, BorderLayout.SOUTH);
//         }

//         public void updateInfo(int osn, int hsn, boolean inCriticalSection) {
//             osnLabel.setText("OSN: " + osn);
//             hsnLabel.setText("HSN: " + hsn);
//             inCriticalSectionLabel.setText("In Critical Section: " + inCriticalSection);
//         }

//         public void addLogEntry(String logEntry) {
//             logTextArea.append(logEntry + "\n");
//         }
//     }
// }

//********* */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RicartAgrawalaGUI extends JFrame {
    private Map<Integer, ProcessPanel> processPanels;

    public RicartAgrawalaGUI() {
        processPanels = new HashMap<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Ricart-Agrawala Mutex");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 10, 10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 10; i++) {
            ProcessPanel processPanel = new ProcessPanel(i);
            processPanels.put(i, processPanel);
            mainPanel.add(processPanel);
        }

        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateProcessInfo(int processId, int osn, int hsn, boolean inCriticalSection) {
        SwingUtilities.invokeLater(() -> {
            ProcessPanel processPanel = processPanels.get(processId);
            processPanel.updateInfo(osn, hsn, inCriticalSection);
        });
    }

    public void addLogEntry(int processId, String logEntry) {
        SwingUtilities.invokeLater(() -> {
            ProcessPanel processPanel = processPanels.get(processId);
            processPanel.addLogEntry(logEntry);
        });
    }

    private static class ProcessPanel extends JPanel {
        private JLabel processIdLabel;
        private JLabel osnLabel;
        private JLabel hsnLabel;
        private JLabel inCriticalSectionLabel;
        private JTextArea logTextArea;

        public ProcessPanel(int processId) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEtchedBorder());

            processIdLabel = new JLabel("Process ID: " + processId);
            osnLabel = new JLabel("OSN: ");
            hsnLabel = new JLabel("HSN: ");
            inCriticalSectionLabel = new JLabel("In Critical Section: ");

            JPanel infoPanel = new JPanel(new GridLayout(4, 1));
            infoPanel.add(processIdLabel);
            infoPanel.add(osnLabel);
            infoPanel.add(hsnLabel);
            infoPanel.add(inCriticalSectionLabel);

            logTextArea = new JTextArea();
            logTextArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(logTextArea);

            add(infoPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
        }

        public void updateInfo(int osn, int hsn, boolean inCriticalSection) {
            osnLabel.setText("OSN: " + osn);
            hsnLabel.setText("HSN: " + hsn);
            // inCriticalSectionLabel.setText("SC: " + inCriticalSection);
            if (inCriticalSection) {
                inCriticalSectionLabel.setText("SC: " + inCriticalSection);
                inCriticalSectionLabel.setForeground(Color.GREEN);
            } else {
                inCriticalSectionLabel.setText("SC: " + inCriticalSection);
                inCriticalSectionLabel.setForeground(Color.RED);
            }
        }

        public void addLogEntry(String logEntry) {
            logTextArea.append(logEntry + "\n");
        }
    }
}