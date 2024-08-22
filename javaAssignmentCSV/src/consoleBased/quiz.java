package consoleBased;
import java.util.LinkedList;

public class quiz {
    private LinkedList<quizCatagory> quiz;
    private csvFunctions csvFunctions;

    public quiz() {
        quiz = new LinkedList<>();
        csvFunctions = new csvFunctions(this);
        csvFunctions.loadQuizFromCSV();
    }

    public void addCatagory(quizCatagory quizCatagory) {
        this.quiz.add(quizCatagory);
    }

    public quizCatagory getQuestionCatagory(int index) {
        quizCatagory q = quiz.get(index);
        return q;
    }

    public LinkedList<quizCatagory> getQuizCatagories() {
        return this.quiz;
    }

    public quizQuestion createQuizQuestion(String question, String answer) {
        quizQuestion quizQuestion = new quizQuestion(question,answer);
        return quizQuestion;
    }

    public void addQuizQuestion(int catagory, String question, String answer) {
        quizQuestion q = createQuizQuestion(question, answer);
        appendQuizQuestion(catagory, q);
        csvFunctions.saveSingleQuestiontoCsv(catagory, q);
    }

    public void appendQuizQuestion(int catagory, quizQuestion q) {
        getQuizCatagories().get(catagory).addQuizQuestion(q);
    }

    public void saveSingleCsv(quizCatagory quizCatagory) {
        csvFunctions.saveSingleCsv(quizCatagory);
    }
}
