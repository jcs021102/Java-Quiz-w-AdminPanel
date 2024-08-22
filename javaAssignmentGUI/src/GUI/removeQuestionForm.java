package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import guiBased.quiz;
import guiBased.quizCatagory;

public class removeQuestionForm extends JFrame {
    private JComboBox<String> categoryDropdown;
    private JComboBox<String> questionDropdown;
    private static quiz quiz;

    public removeQuestionForm(quiz quiz) {
        removeQuestionForm.quiz = quiz;
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JPanel panel = new JPanel();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        add(panel);
        setVisible(true);
        panel.setLayout(null);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(50, 20, 80, 25);
        panel.add(categoryLabel);

        // Populates the catagories
        String[] categories = new String[quiz.getQuizCatagories().size()];
        for (int i = 0; i < quiz.getQuizCatagories().size(); i++) {
            categories[i] = (i + 1) + ". " + quiz.getQuizCatagoryName(i);
        }
        categoryDropdown = new JComboBox<>(categories);
        categoryDropdown.setBounds(150, 20, 200, 25);
        panel.add(categoryDropdown);

        JLabel questionLabel = new JLabel("Question:");
        questionLabel.setBounds(50, 60, 80, 25);
        panel.add(questionLabel);

        questionDropdown = new JComboBox<>();
        questionDropdown.setBounds(150, 60, 200, 25);
        panel.add(questionDropdown);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 100, 80, 25);
        panel.add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(200, 100, 80, 25);
        panel.add(cancelButton);

        // Removes the selected question from the LinkedList and the CSV
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryDropdown.getSelectedItem();
                String selectedQuestion = (String) questionDropdown.getSelectedItem();

                quizCatagory chosenCatagory = quiz.getQuizCatagories()
                        .get(Integer.valueOf(selectedCategory.split("\\.")[0]) - 1);

                chosenCatagory.removeQuizQuestion(Integer.valueOf(selectedQuestion.split("\\.")[0]) - 1);
                quiz.saveSingleCsv(chosenCatagory);

                updateQuestionDropdown();
                JOptionPane.showMessageDialog(panel, "Removed");
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        categoryDropdown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateQuestionDropdown();
            }
        });
        String categoryDefault = categories[0];
        categoryDropdown.setSelectedItem(categoryDefault);
        updateQuestionDropdown();
    }

    //Updates the dropdown with the chosen catagory's Questions
    private void updateQuestionDropdown() {
        String selectedCategory = (String) categoryDropdown.getSelectedItem();
        if (selectedCategory != null) {
            int category = Integer.valueOf(selectedCategory.split("\\.")[0]) - 1;

            String[] questions = new String[quiz.getQuizCatagories().get(category).getQuizQuestions().size()];
            for (int i = 0; i < questions.length; i++) {
                questions[i] = (i + 1) + ". "
                        + quiz.getQuizCatagories().get(category).getQuizQuestions().get(i).getQuestion();
            }

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(questions);
            questionDropdown.setModel(model);
        }
    }

}
