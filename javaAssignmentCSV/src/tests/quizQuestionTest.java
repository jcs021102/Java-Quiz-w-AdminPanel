package tests;

import consoleBased.*;
import static org.junit.Assert.*;
import org.junit.Test;

class QuizQuestionTest {

    @Test
    void testDefaultConstructor() {
        quizQuestion question = new quizQuestion();
        assertEquals("Empty Question", question.getQuestion());
        assertEquals("Empty Answer", question.getAnswer());
    }
    @Test
    void testConstructor() {
        String questionText = "What is the capital of France?";
        String answerText = "Paris";
        quizQuestion question = new quizQuestion(questionText, answerText);
        assertEquals(questionText, question.getQuestion());
        assertEquals(answerText, question.getAnswer());
    }

    @Test
    void testCompareToEqualQuestions() {
        quizQuestion question1 = new quizQuestion("Question", "Answer");
        quizQuestion question2 = new quizQuestion("Question", "Answer2");
        assertEquals(0, question1.compareTo(question2));
    }

    @Test
    void testCompareToDifferentQuestions() {
        quizQuestion question1 = new quizQuestion("Question1", "Answer");
        quizQuestion question2 = new quizQuestion("Question2", "Answer");
        assertEquals(1, question1.compareTo(question2));
    }

    @Test
    void testIsCorrectWithCorrectAnswer() {
        quizQuestion question = new quizQuestion("Question", "Answer");
        assertTrue(question.isCorrect("answer"));
    }

    @Test
    void testIsCorrectWithIncorrectAnswer() {
        quizQuestion question = new quizQuestion("Question", "Answer");
        assertFalse(question.isCorrect("wrong answer"));
    }
}
