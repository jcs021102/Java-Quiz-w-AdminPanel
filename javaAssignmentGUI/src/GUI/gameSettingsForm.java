package GUI;

import guiBased.quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameSettingsForm extends JFrame {

    private JTextField textField1;
    private quiz quiz;

    public gameSettingsForm(quiz quiz) {
        this.quiz = quiz;
        initComponents();
    }

    private void initComponents() {
        setTitle("Game Settings");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label1 = new JLabel("Max Score Till Win:");
        textField1 = new JTextField();
        textField1.setText("" + quiz.getMaxScore());

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Enter New Max Score:"));
        inputPanel.add(textField1);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        panel.add(label1, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // If new input is valid, sets the maxScore to that
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int maxScore = quiz.getMaxScore();
                String text1 = textField1.getText();
                try {
                    maxScore = Integer.parseInt(text1);
                    if (maxScore >= 1) {
                        quiz.setMaxScore(maxScore);
                        JOptionPane.showMessageDialog(panel, "Success!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(panel, "Invalid Input, please try again");
                        textField1.setText("" + maxScore);
                    }
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(panel, "Invalid Input, please try again");
                    textField1.setText("" + maxScore);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
