package org.necrotic.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RSPSGuideUI {

    private JFrame frame;
    private JList<String> guideList;
    private JTextArea textArea;
    private String[] options;
    private String[] content;

    public RSPSGuideUI() {
        // Data for the options and their corresponding content
        options = new String[] {
                "Getting Started",
                "Combat Guide",
                "Quests and Rewards",
                "Server Rules"
        };

        content = new String[] {
                "Welcome to the server! To get started, you'll need to...\n\n1. Create a character.\n2. Go to the training area.\n3. Start completing beginner quests...",
                "Combat Guide: Combat in this server includes... \n\n1. Fighting NPCs in the Arena.\n2. Using different weapons and armor...\n3. Leveling your combat stats.",
                "Quests: The server offers several quests to earn rewards.\n\n1. Quest 1: Killing the Dragon...\n2. Quest 2: Gathering the Herbs...",
                "Server Rules:\n1. No cheating or botting.\n2. No offensive language.\n3. Respect other players and staff members..."
        };

        // Setting up the frame and layout
        frame = new JFrame("RSPS Guide");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  // Center the window on the screen
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(48, 25, 52));// Use BorderLayout for the main layout
        // Panel for the left side - JList for clickable options
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        // Set the background color of the frame to dark purple
        frame.getContentPane().setBackground(new Color(48, 25, 52));  // Dark Purple RGB color
        frame.getContentPane().setBackground(Color.BLACK);  // Set the entire frame to black

        frame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));  // 5px black border

        // Create the JList for displaying options on the left
        guideList = new JList<>(options);
        guideList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        guideList.setSelectedIndex(0);
        guideList.setBackground(new Color(58, 30, 65)); // Darker purple for list background
        guideList.setForeground(Color.WHITE);// Select the first item by default
        guideList.addListSelectionListener(e -> updateContent());

        // Add the list to a JScrollPane (in case the options are long)
        leftPanel.add(new JScrollPane(guideList), BorderLayout.CENTER);

        // Panel for the right side - where information will be displayed
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        // Create a JTextArea to display the selected option's content
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(content[0]); // Show content for the first option by default
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(58, 30, 65)); // Dark purple background for text area
        textArea.setForeground(Color.WHITE); // White text for readability
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        // Add the text area to the right panel
        rightPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Add both panels to the frame (leftPanel and rightPanel)
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);

        // Display the frame
        frame.setVisible(true);
    }

    // Method to update the content displayed on the right side based on the selected option
    private void updateContent() {
        int selectedIndex = guideList.getSelectedIndex();
        textArea.setText(content[selectedIndex]); // Update the text area with the selected option's content
    }

    // Main method for testing the UI when running standalone
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RSPSGuideUI());
    }
}