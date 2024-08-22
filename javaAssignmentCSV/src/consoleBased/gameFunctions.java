package consoleBased;
import java.util.Scanner;

public class gameFunctions {
    Scanner sc;
    quiz quiz;
    player player;

    public gameFunctions(quiz quiz) {
        sc = new Scanner(System.in);
        player = new player();
        this.quiz = quiz;
    }

    public void playGame() {
        do {
            String choice = promptDieRoll();
            if (choice.compareTo("roll") == 0) {
                do {
                    quizQuestion chosenQuestion = chooseQuestion();
                    answerAndResponse(chosenQuestion);
                } while ((player.getLives() > 0) && 10 > player.getSolved());
                winOrLose();
            } else if (choice.compareTo("ff") == 0) {
                System.out.println("Better Luck Next Time");
                break;
            } else {
                System.out.println("Type roll to roll the die, or ff to give up");
            }
        } while (true);
    }

    public int dice() {
        return (int) (Math.random() * 6) + 1;
    }

    public String promptDieRoll() {
        // prompt user to choose to roll the dice
        System.out.println("Would you like to roll the dice?");
        return sc.nextLine().toLowerCase();
    }

    public quizQuestion chooseQuestion() {
        int roll = dice();
        System.out.println("You rolled a " + roll + "!");
        roll -= 1;
        quizCatagory qc = quiz.getQuestionCatagory(roll);
        quizQuestion chosenQuestion = qc.getRandomQuestionONCE();
        System.out.println(chosenQuestion.getQuestion());
        return chosenQuestion;
    }

    public void answerAndResponse(quizQuestion chosenQuestion) {
        // user playing the game and getting a response
        String answer = sc.nextLine().toLowerCase();
        boolean isCorrect = chosenQuestion.isCorrect(answer);
        // response to user's choice
        response(isCorrect);
    }

    public void response(boolean isCorrect) {
        if (isCorrect) {
            player.incSolved();
            System.out.println("Correct!");
        } else {
            player.decLives();
            System.out.println("Incorrect Answer, try again. " + player.getLives() + " remaining.");
        }
    }

    public void winOrLose() {
        if (!(player.getLives() > 0)) {
            System.out.println("Game Over :c");
        } else {
            System.out.println("You Win!");
        }
    }
}