package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.Path;

import guiBased.quiz;

public class addMultipleQuestionForm extends JFrame {

    private quiz quiz;
    private JComboBox<String> categoryDropdown;
    private Path selectedFilePath;

    public addMultipleQuestionForm(quiz quiz) {
        this.quiz = quiz;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public void createAndShowGUI() {
        // Set up the JFrame
        setTitle("Add Multiple Questions");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 200);

        // Create components
        JButton uploadButton = new JButton("Upload File");
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        // Create a JComboBox with categories
        String[] options = new String[quiz.getQuizCatagories().size()];
        for (int i = 0; i < quiz.getQuizCatagories().size(); i++) {
            options[i] = (i + 1) + ". " + quiz.getQuizCatagoryName(i);
        }
        categoryDropdown = new JComboBox<>(options);

        setLayout(new GridLayout(3, 2));

        add(uploadButton);
        add(categoryDropdown);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel);

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(addMultipleQuestionForm.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // User selected a file
                    selectedFilePath = fileChooser.getSelectedFile().toPath();
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String catag = categoryDropdown.getSelectedItem().toString().split("\\.")[0];
                int catagIndex = Integer.parseInt(catag) - 1;

                quiz.savePathToCSV(quiz.getQuestionCatagory(catagIndex), selectedFilePath);
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle cancel button action
                dispose();
            }
        });

        // Make the frame visible
        setVisible(true);
    }
}
