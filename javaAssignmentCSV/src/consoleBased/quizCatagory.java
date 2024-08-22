package consoleBased;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.TreeSet;

public class quizCatagory {
    private TreeSet<quizQuestion> blacklist = new TreeSet<>();
    private LinkedList<quizQuestion> quizCatagory;
    private Path CsvPath;
    private int index;

    public quizCatagory(Path filename, int index) {
        quizCatagory = new LinkedList<>();
        this.CsvPath = filename;
        this.index = index;
    }

    // gets a random question, and removes it from the list
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

    public Path getCsvPath() {
        return CsvPath;
    }

    public LinkedList<quizQuestion> getQuizQuestions() {
        return quizCatagory;
    }

    public int getIndex() {
        return index;
    }

}