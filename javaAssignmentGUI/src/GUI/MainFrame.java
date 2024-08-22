package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import guiBased.player;
import guiBased.quiz;

public class MainFrame extends JFrame {

    private quiz quiz;
    private static player player;
    private static JFrame frame;

    public MainFrame(quiz quiz, player player) {
        this.quiz = quiz;
        MainFrame.player = player;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        frame.getContentPane().add(panel);

        JLabel titleLabel = new JLabel("Main Menu");
        titleLabel.setBounds(150, 20, 100, 25);
        panel.add(titleLabel);

        JButton playGameButton = new JButton("Play Game");
        playGameButton.setBounds(50, 60, 150, 25);
        panel.add(playGameButton);

        JButton highscoreButton = new JButton("View Highscores");
        highscoreButton.setBounds(200, 60, 150, 25);
        panel.add(highscoreButton);

        JButton adminMenuButton = new JButton("Admin Menu");
        adminMenuButton.setBounds(50, 100, 150, 25);
        panel.add(adminMenuButton);

        JButton exitButton = new JButton("Exit Game");
        exitButton.setBounds(200, 100, 150, 25);
        panel.add(exitButton);

        // Add action listeners to the buttons
        adminMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new adminFrame(quiz);
            }
        });

        playGameButton.addActionListener(new ActionListener() {
            int minQuestions = 10;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Validation
                if (quiz.isMinQuestionsFound(minQuestions)) {
                    player.reset();
                    new gameForm(quiz, player);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Not enough questions in each catagory to start the quiz, please make sure there are at least "
                                    + minQuestions + " in each.");
                }

            }
        });

        highscoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new highscoreFrame();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        promptForName(frame);
    }

    // Prompts the user for his name
    private static void promptForName(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Enter Your Name", true);
        JPanel panel = new JPanel(new GridLayout(3, 1));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameTextField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredName = nameTextField.getText();
                if (!enteredName.equals("")) {
                    JOptionPane.showMessageDialog(parentFrame, "Hello, " + enteredName + "!");
                    player.setName(enteredName);
                    dialog.dispose(); // Close the dialog after submitting
                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Please Enter a Name");
                }
            }
        });

        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(submitButton);

        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.getContentPane().add(panel);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }
}
