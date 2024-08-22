package guiBased;

public class quizQuestion implements Comparable<quizQuestion> {
    private String question;
    private String answer;

    public quizQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    // Verifies the answer given is the correct one
    public boolean isCorrect(String userAnswer) {
        String correctAnswer = this.answer.toLowerCase();
        return correctAnswer.equals(userAnswer) ? true : false;
    }

    // This override exists to solve an issue with the .contains of the TreeSet when
    // dealing with the quizQuestion Object
    @Override
    public int compareTo(quizQuestion q) {
        return q.getQuestion().equals(this.getQuestion()) ? 0 : 1;
    }
}
