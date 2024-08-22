package guiBased;

import java.nio.file.Path;
import java.util.LinkedList;

public class quiz {
    private LinkedList<quizCatagory> quiz;
    //
    // sets the game's win condition
    //
    private int maxScore;

    // To Grade the assignment based on SQL or CSV the datatype for dataFunctions
    // should refrence either sqlFunctions or csvFunctions respectively
    private sqlFunctions dataFunctions;

    public quiz() {
        quiz = new LinkedList<>();
        dataFunctions = new sqlFunctions(this);
        dataFunctions.loadQuizQuestions();
        maxScore = 10;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void addCatagory(quizCatagory quizCatagory) {
        quiz.add(quizCatagory);
    }

    public quizCatagory getQuestionCatagory(int index) {
        return quiz.get(index);
    }

    public LinkedList<quizCatagory> getQuizCatagories() {
        return quiz;
    }

    // gets the quiz catagory's name, this is done in a try catch to account for the
    // case of swapping between sqlFunctions and csvFunctions, and is the only
    // situation where an exception had to be made to the code
    public String getQuizCatagoryName(int index) {
        String catagoryName;
        try {
            catagoryName = getQuestionCatagory(index).getCsvPath().toString().split("\\\\")[1];
            catagoryName = catagoryName.split("\\.")[0];
        } catch (Exception e) {
            catagoryName = getQuestionCatagory(index).getCsvPath();
        }
        return catagoryName;
    }

    // creates a quizQuestion based on question and answer
    public quizQuestion createQuizQuestion(String question, String answer) {
        quizQuestion quizQuestion = new quizQuestion(question, answer);
        return quizQuestion;
    }

    // adds a quizQuestion to the linkedList and the CSV
    public void addQuizQuestion(int catagory, String question, String answer) {
        quizQuestion q = createQuizQuestion(question, answer);
        addQuizQuestionToList(catagory, q);
        dataFunctions.saveSingleQuestiontoCsv(catagory, q);
    }

    // Adds a quiz question to a linked list by index
    private void addQuizQuestionToList(int index, quizQuestion q) {
        getQuizCatagories().get(index).addQuizQuestion(q);
    }

    public void saveSingleCsv(quizCatagory quizCatagory) {
        dataFunctions.saveSingleCsv(quizCatagory);
    }

    // intermediary method for dataFuctions Method
    public void savePathToCSV(quizCatagory quizCatagory, Path filePath) {
        dataFunctions.savePathToCSV(quizCatagory, filePath);
    }

    // Validates that the quiz has enough questions to viably start a game.
    // If the number of questions is below 6, the program will fail, thus is
    // explicitly defined as a hard minimum
    public boolean isMinQuestionsFound(int minQuestions) {
        if (minQuestions < 6) {
            minQuestions = 6;
        }
        for (quizCatagory quizCatagory : quiz) {
            if (quizCatagory.getQuizQuestions().size() <= minQuestions) {
                return false;
            }
        }
        return true;
    }
}
