package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import guiBased.quiz;

public class addQuestionForm {
    private static quiz quiz;
    private static JFrame frame;

    public addQuestionForm(quiz quiz) {
        addQuestionForm.quiz = quiz;
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Add Single Question");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel label1 = new JLabel("Question:");
        label1.setBounds(50, 20, 80, 25);
        panel.add(label1);

        JTextField textField1 = new JTextField(20);
        textField1.setBounds(150, 20, 200, 25);
        panel.add(textField1);

        JLabel label2 = new JLabel("Answer:");
        label2.setBounds(50, 60, 80, 25);
        panel.add(label2);

        JTextField textField2 = new JTextField(20);
        textField2.setBounds(150, 60, 200, 25);
        panel.add(textField2);

        JLabel dropdownLabel = new JLabel("Catagory:");
        dropdownLabel.setBounds(50, 100, 80, 25);
        panel.add(dropdownLabel);

        String[] options = new String[quiz.getQuizCatagories().size()];
        for (int i = 0; i < quiz.getQuizCatagories().size(); i++) {
            options[i] = (i + 1) + ". " + quiz.getQuizCatagoryName(i);
        }
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.setBounds(150, 100, 150, 25);
        panel.add(dropdown);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 140, 80, 25);
        panel.add(submitButton);

        JButton closeButton = new JButton("close");
        closeButton.setBounds(200, 140, 80, 25);
        panel.add(closeButton);

        // if valid adds the question to the chosen catagory
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String question = textField1.getText();
                String answer = textField2.getText();
                if (question.equals("") || answer.equals("")) {
                    JOptionPane.showMessageDialog(panel, "Invalid Input");
                } else {
                    String dropdownOpt = (String) dropdown.getSelectedItem();
                    int catagory = Integer.parseInt(dropdownOpt.split("\\.")[0]);
                    quiz.addQuizQuestion(catagory - 1, question, answer);

                    textField1.setText("");
                    textField2.setText("");

                    JOptionPane.showMessageDialog(panel, dropdownOpt.split("\\.")[1] + " has been updated");
                }
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }
}
