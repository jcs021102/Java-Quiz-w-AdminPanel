package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import guiBased.quiz;

public class adminFrame extends JFrame {
    private quiz quiz;
    private JFrame frame;

    public adminFrame(quiz quiz) {
        this.quiz = quiz;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Main Menu");
        titleLabel.setBounds(150, 20, 100, 25);
        panel.add(titleLabel);

        JButton addQuestionButton = new JButton("Add Question");
        addQuestionButton.setBounds(50, 60, 150, 25);
        panel.add(addQuestionButton);

        JButton removeQuestionButton = new JButton("Remove Question");
        removeQuestionButton.setBounds(200, 60, 150, 25);
        panel.add(removeQuestionButton);

        JButton addMultpleQuestionButton = new JButton("Add Multple Questions");
        addMultpleQuestionButton.setBounds(50, 100, 150, 25);
        panel.add(addMultpleQuestionButton);

        JButton setupGameButton = new JButton("Configure Game");
        setupGameButton.setBounds(200, 100, 150, 25);
        panel.add(setupGameButton);

        JButton exitButton = new JButton("Exit To Main Menu");
        exitButton.setBounds(50, 140, 300, 25);
        panel.add(exitButton);

        // Add action listeners to the buttons
        addQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new addQuestionForm(quiz);
            }
        });

        addMultpleQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new addMultipleQuestionForm(quiz);
            }
        });

        removeQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new removeQuestionForm(quiz);
            }
        });

        setupGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new gameSettingsForm(quiz);
            }
        });
        

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

    }
}
