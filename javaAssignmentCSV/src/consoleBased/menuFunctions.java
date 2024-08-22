package consoleBased;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class menuFunctions {
    Scanner sc;
    quiz quiz;

    public menuFunctions(quiz quiz) {
        sc = new Scanner(System.in);
        this.quiz = quiz;
    }

    public void mainPrompt() {
        printMainMenuOptions();
        int choice = 0;
        String input = sc.nextLine();
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
        }
        switch (choice) {
            case 1:// add a question
                addMultipleQuestion();
                break;
            case 2:// remove a question
                System.out.println("===Remove a Question===");
                removeQuizQuestion();
                break;
            case 3:// start the game
                gameFunctions game = new gameFunctions(quiz);
                game.playGame();
                break;
            case 4:// exit the application
                System.out.println("Application Closed");
                break;
            default:// if invalid option
                System.out.println("Invalid Input, please try again");
                break;
        }
        // if exit isnt chosen
        if (choice != 4) {
            mainPrompt();
        }
    }

    public void addMultipleQuestion() {
        addQuestion();
        do {
            System.out.println("Would you like to add another? Y/N");
            String choiceStr = sc.nextLine();
            choiceStr.toLowerCase();
            char choice = choiceStr.toCharArray()[0];
            if (choice == 'n') {
                break;
            }
            if (choice == 'y') {
                addMultipleQuestion();
            }
            System.out.println("Invalid input, pelase try again");
        } while (true);
    }

    public void addQuestion() {
        String question = "";
        String answer = "";
        System.out.println("And under which of the following catagories does it go");

        int catagory = promptQuestionCatagories().getIndex();

        System.out.println("==Adding a Question==");
        System.out.println("Please insert the question");
        question = sc.nextLine();

        System.out.println("Please insert the answer");
        answer = sc.nextLine();
        quiz.addQuizQuestion(catagory, question, answer);
    }

    public quizCatagory promptQuestionCatagories() {
        int choice = 0;
        int catagorySize = quiz.getQuizCatagories().size();
        do {
            printCatagoryList();
            String str = sc.nextLine();
            try {
                choice = Integer.parseInt(str);
            } catch (NumberFormatException ex) {
                choice = 0;
            }
            choice--;
            if (choice >= catagorySize || choice < 0) {
                System.out.println("Invalid Input");
            } else {
                break;
            }
        } while (true);
        return quiz.getQuestionCatagory(choice);
    }

    public void removeQuizQuestion() {
        // variable defenition
        quizCatagory chosenCatagory;
        int chosenQuestion;

        // prompts chosen catagory & user response
        chosenCatagory = promptQuestionCatagories();

        // prompts chosen question & user response
        chosenQuestion = promptPrintQuestions(chosenCatagory);

        // validates choice and removes question
        if (chosenQuestion == -1) {
            System.out.println("Invalid input, please try again");
            removeQuizQuestion();
        } else {
            chosenCatagory.removeQuizQuestion(chosenQuestion);
            quiz.saveSingleCsv(chosenCatagory);
        }

    }

    public int promptPrintQuestions(quizCatagory quizCatagory) {
        // prompts chosen question & user response
        printQuestions(quizCatagory);
        int chosenQuestion = 0;
        String str = sc.nextLine();
        try {
            chosenQuestion = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            return -1;
        }
        chosenQuestion--;

        // validates choice and returns -1 if invalid
        if (chosenQuestion >= 0 && chosenQuestion < quizCatagory.getQuizQuestions().size()) {
            return chosenQuestion;
        }
        return -1;
    }

    public void printQuestions(quizCatagory quizCatagory) {
        int i = 0;
        System.out.println("----------");
        if (quizCatagory.getQuizQuestions().size() != 0) {
            for (quizQuestion quizQuestion : quizCatagory.getQuizQuestions()) {
                i++;
                System.out.println(i + ". " + quizQuestion.getQuestion());
            }
        } else {
            System.out.println("No options available");
        }
        System.out.println("----------");
    }

    public void printCatagoryList() {
        String folderPath = "quiz_data";
        int i = 0;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(folderPath))) {
            for (Path path : directoryStream) {
                i++;
                String catagoryName = path.toString().split("\\\\")[1];
                catagoryName = catagoryName.split("\\.")[0];
                System.out.println(i + ". " + catagoryName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMainMenuOptions() {
        System.out.println("===Main Menu===");
        System.out.println("1. Add a question to a catagory");
        System.out.println("2. Remove a question from a catagory");
        System.out.println("3. Play the Game");
        System.out.println("4. Exit");
    }
}
