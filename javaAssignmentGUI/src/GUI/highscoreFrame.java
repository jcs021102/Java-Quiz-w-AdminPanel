package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.*;

import guiBased.highscoreFunctions;

public class highscoreFrame {
    private highscoreFunctions hsFunc;
    private JFrame frame;
    private JTable table;
    private JButton clearButton;
    private JButton closeButton;

    public highscoreFrame() {
        hsFunc = new highscoreFunctions();
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("Highscores");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Score");

        // Add data to the table model
        for (Object row : hsFunc.getHighscores()) {
            Object[] rowData = (Object[]) row;
            tableModel.addRow(rowData);
        }

        // Create table with the model
        table = new JTable(tableModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Set up the frame layout
        frame.setLayout(new BorderLayout());

        // Add label at the top
        JLabel titleLabel = new JLabel("Highscores", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Add table to the center
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add buttons at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        clearButton = new JButton("Clear");
        closeButton = new JButton("Close");

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to clear highscores
                hsFunc.clearFile();
                frame.dispose();
                createAndShowGUI();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        buttonPanel.add(clearButton);
        buttonPanel.add(closeButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
