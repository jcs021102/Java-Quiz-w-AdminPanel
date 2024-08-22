package guiBased;

import java.util.LinkedList;
import java.util.TreeSet;

public class quizCatagory {
    private TreeSet<quizQuestion> blacklist = new TreeSet<>();
    private LinkedList<quizQuestion> quizCatagory;
    private String CsvPath;

    public quizCatagory(String filename) {
        quizCatagory = new LinkedList<>();
        this.CsvPath = filename;
    }

    // gets a random question, and blacklists it from being select again, within the
    // same session
    public quizQuestion getRandomQuestionONCE() {
        if (quizCatagory.isEmpty()) {
            throw new RuntimeException("No questions available.");
        }

        int randomNum;
        quizQuestion q;
        do {
            randomNum = (int) (Math.random() * quizCatagory.size());
            q = quizCatagory.get(randomNum);
        } while (blacklist.contains(q));

        blacklist.add(q);
        return q;
    }

    public void addQuizQuestion(quizQuestion q) {
        quizCatagory.add(q);
    }

    public void removeQuizQuestion(int index) {
        quizCatagory.remove(index);
    }

    public String getCsvPath() {
        return CsvPath;
    }

    public LinkedList<quizQuestion> getQuizQuestions() {
        return quizCatagory;
    }

}