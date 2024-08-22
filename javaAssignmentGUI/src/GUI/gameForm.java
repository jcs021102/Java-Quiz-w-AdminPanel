package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import guiBased.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class gameForm {
    private static quiz quiz;
    private static player player;
    private static quizQuestion currentQuestion;

    private highscoreFunctions hsFunc;
    private JFrame frame;
    private JLabel categoryLabel;
    private JLabel diceLabel;
    private JLabel questionLabel;
    private JTextField answerTextField;
    private JLabel feedbackLabel;
    private JButton diceButton;
    private JButton submitButton;
    private JLabel scoreLabel;
    private JLabel livesLabel;

    public gameForm(quiz quiz, player player) {
        gameForm.quiz = quiz;
        gameForm.player = player;
        hsFunc = new highscoreFunctions();

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("Quiz Game");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);

        categoryLabel = new JLabel("Roll for a catagory");
        diceLabel = new JLabel("Last Roll: ?");
        questionLabel = new JLabel("The Question will be displayed here");
        answerTextField = new JTextField(10);
        feedbackLabel = new JLabel();
        diceButton = new JButton("Roll Dice");
        submitButton = new JButton("Submit");
        scoreLabel = new JLabel("Score: " + player.getSolved());
        livesLabel = new JLabel("Lives: " + player.getLives());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(categoryLabel);

        panel.add(topPanel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(diceLabel);
        leftPanel.add(diceButton);

        panel.add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(questionLabel, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(answerTextField, BorderLayout.CENTER);
        bottomPanel.add(submitButton, BorderLayout.EAST);
        bottomPanel.add(feedbackLabel, BorderLayout.SOUTH);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(scoreLabel);
        rightPanel.add(livesLabel);

        panel.add(rightPanel, BorderLayout.EAST);
        frame.add(panel);
        frame.setVisible(true);
        submitButton.setEnabled(false);

        // roll the die
        diceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dice();
            }
        });

        // Unblock the ability to roll if the game is still going
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String answer = answerTextField.getText();
                isCorrect(answer);

                submitButton.setEnabled(false);
                answerTextField.setText("");
                if (player.getLives() > 0 || player.getSolved() <= quiz.getMaxScore()) {
                    diceButton.setEnabled(true);
                }
            }
        });
    }

    // rolls the die, and updated apropriate labels

    private void dice() {
        int roll = (int) (Math.random() * 6) + 1;
        diceButton.setEnabled(false);
        submitButton.setEnabled(true);
        diceLabel.setText("Last Roll: " + roll);
        feedbackLabel.setText("");
        setCatagoryLabel(roll - 1);
    }

    private void setCatagoryLabel(int catagoryIndex) {
        categoryLabel.setText("Catagory: " + quiz.getQuizCatagoryName(catagoryIndex));
        setQuestionLabel(quiz.getQuestionCatagory(catagoryIndex));
    }

    private void setQuestionLabel(quizCatagory qCatagory) {
        currentQuestion = qCatagory.getRandomQuestionONCE();
        questionLabel.setText("Question: " + currentQuestion.getQuestion());
    }

    // checks if the response is valid, and blocks / unblocks buttons accordingly
    // checks if the game has been won or lost
    private void isCorrect(String answer) {
        answer = answer.toLowerCase();
        boolean isCorrectResponse = currentQuestion.isCorrect(answer);

        if (isCorrectResponse) {
            feedbackLabel.setText("Correct! Roll again.");
            player.incSolved();
            scoreLabel.setText("Score: " + player.getSolved());
        } else {
            feedbackLabel.setText("Incorrect. Try again.");
            player.decLives();
            livesLabel.setText("Lives: " + player.getLives());
        }
        if (player.getLives() <= 0) {
            gameOver();
        }
        if (player.getSolved() == quiz.getMaxScore()) {
            win();
        }

    }

    private void gameOver() {
        diceButton.setEnabled(false);
        submitButton.setEnabled(false);
        feedbackLabel.setText("Game Over. Your final score is: " + player.getSolved());
        JOptionPane.showMessageDialog(frame, "Game Over. Your final score is: " + player.getSolved());
        frame.dispose();
    }

    private void win() {
        diceButton.setEnabled(false);
        submitButton.setEnabled(false);
        feedbackLabel.setText("Winner! Your final score is: " + player.getSolved());
        JOptionPane.showMessageDialog(frame, "Winner! Your final score is: " + player.getSolved());
        frame.dispose();
        // saves score to highscores list
        hsFunc.saveScore(player.getName(), player.getSolved());
    }
}
