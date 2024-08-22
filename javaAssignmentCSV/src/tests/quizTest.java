package tests;

import java.nio.file.Path;

import org.junit.Test;

import consoleBased.quiz;
import consoleBased.quizCatagory;
import consoleBased.quizQuestion;

import static org.junit.Assert.*;

public class quizTest {

    @Test
    public void testConstructorAndAddCategory() {

        quiz quizInstance = new quiz();
        quizCatagory catagory1 = new quizCatagory(Path.of("category1.csv"), 0);

        quizInstance.addCatagory(catagory1);

        assertEquals(1, quizInstance.getQuizCatagories().size());
        assertEquals(catagory1, quizInstance.getQuizCatagories().get(0));
    }

    @Test
    public void testGetQuestionCategory() {
        quiz quizInstance = new quiz();
        quizCatagory catagory1 = new quizCatagory(Path.of("category1.csv"), 0);
        quizCatagory catagory2 = new quizCatagory(Path.of("category2.csv"), 1);

        quizInstance.addCatagory(catagory1);
        quizInstance.addCatagory(catagory2);

        assertEquals(catagory1, quizInstance.getQuestionCatagory(0));
        assertEquals(catagory2, quizInstance.getQuestionCatagory(1));
    }

    @Test
    public void testGetQuizCategories() {
        quiz quizInstance = new quiz();
        quizCatagory catagory1 = new quizCatagory(Path.of("category1.csv"), 0);
        quizCatagory catagory2 = new quizCatagory(Path.of("category2.csv"), 1);

        quizInstance.addCatagory(catagory1);
        quizInstance.addCatagory(catagory2);

        assertEquals(2, quizInstance.getQuizCatagories().size());
        assertTrue(quizInstance.getQuizCatagories().contains(catagory1));
        assertTrue(quizInstance.getQuizCatagories().contains(catagory2));
    }

    @Test
    public void testCreateQuizQuestion() {
        quiz quizInstance = new quiz();
        quizQuestion question = quizInstance.createQuizQuestion("What is 2 + 2?", "4");

        assertEquals("What is 2 + 2?", question.getQuestion());
        assertEquals("4", question.getAnswer());
    }

    @Test
    public void testAddQuizQuestion() {
        quiz quizInstance = new quiz();
        quizCatagory catagory = new quizCatagory(Path.of("category.csv"), 0);

        quizInstance.addCatagory(catagory);
        quizInstance.addQuizQuestion(0, "What is 2 + 2?", "4");

        assertEquals(1, catagory.getQuizQuestions().size());
    }

    @Test
    public void testAppendQuizQuestion() {
        quiz quizInstance = new quiz();
        quizCatagory catagory = new quizCatagory(Path.of("category.csv"), 0);

        quizInstance.addCatagory(catagory);
        quizInstance.appendQuizQuestion(0, new quizQuestion("What is 2 + 2?", "4"));

        assertEquals(1, catagory.getQuizQuestions().size());
    }

}
