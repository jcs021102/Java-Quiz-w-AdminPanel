package consoleBased;

public class quizQuestion implements Comparable<quizQuestion> {
    private String question;
    private String answer;

    public quizQuestion() {
        this.question = "Empty Question";
        this.answer = "Empty Answer";
    }

    public quizQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public int compareTo(quizQuestion q) {
        return q.getQuestion().equals(this.getQuestion()) ? 0 : 1;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public boolean isCorrect(String userAnswer) {
        String correctAnswer = this.answer.toLowerCase();
        return correctAnswer.equals(userAnswer) ? true : false;
    }
}
