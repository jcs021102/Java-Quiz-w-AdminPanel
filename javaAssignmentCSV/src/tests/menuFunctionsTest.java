package tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import consoleBased.menuFunctions;
import consoleBased.quiz;
import consoleBased.quizCatagory;
import consoleBased.quizQuestion;

import static org.junit.Assert.*;

public class menuFunctionsTest {

    private quiz quizInstance;
    private menuFunctions menuFunctionsInstance;

    @Before
    public void setUp() {
        quizInstance = new quiz();
        menuFunctionsInstance = new menuFunctions(quizInstance);
    }

    @Test
    public void testMainPrompt() {
        // test 1
        String input = "1\nn\n4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        menuFunctionsInstance.mainPrompt();

        assertEquals(1, quizInstance.getQuizCatagories().size());

        // test 2
        input = "2\n1\n1\n4\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        quizInstance.addCatagory(new quizCatagory(Path.of("category.csv"), 0));
        quizInstance.addQuizQuestion(0, "What is 2 + 2?", "4");

        menuFunctionsInstance.mainPrompt();

        assertEquals(0, quizInstance.getQuizCatagories().get(0).getQuizQuestions().size());
    }

    @Test
    public void testAddMultipleQuestion() {
        // test 1
        String input = "n\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        menuFunctionsInstance.addMultipleQuestion();

        assertEquals(0, quizInstance.getQuizCatagories().size());

        // test 2
        input = "y\nn\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        menuFunctionsInstance.addMultipleQuestion();

        assertEquals(1, quizInstance.getQuizCatagories().size());
    }

    @Test
    public void testAddQuestion() {
        String input = "1\nWhat is the capital of France?\nParis\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        menuFunctionsInstance.addQuestion();

        assertEquals(1, quizInstance.getQuizCatagories().get(0).getQuizQuestions().size());
    }

    @Test
    public void testPromptQuestionCatagories() {
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        quizInstance.addCatagory(new quizCatagory(Path.of("category1.csv"), 0));
        quizInstance.addCatagory(new quizCatagory(Path.of("category2.csv"), 1));

        quizCatagory chosenCatagory = menuFunctionsInstance.promptQuestionCatagories();

        assertEquals("category1.csv", chosenCatagory.getCsvPath().getFileName().toString());
    }

    @Test
    public void testRemoveQuizQuestion() {
        // test 1
        String input = "1\n1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        quizInstance.addCatagory(new quizCatagory(Path.of("category.csv"), 0));
        quizInstance.addQuizQuestion(0, "What is 2 + 2?", "4");

        menuFunctionsInstance.removeQuizQuestion();

        assertEquals(0, quizInstance.getQuizCatagories().get(0).getQuizQuestions().size());

        // test 2
        input = "1\n2\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        quizInstance.addCatagory(new quizCatagory(Path.of("category.csv"), 0));

        menuFunctionsInstance.removeQuizQuestion();

        assertEquals(0, quizInstance.getQuizCatagories().get(0).getQuizQuestions().size());
    }

    @Test
    public void testPromptPrintQuestions() {
        // test 1
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        quizCatagory quizCatagory = new quizCatagory(Path.of("category.csv"), 0);
        quizCatagory.addQuizQuestion(new quizQuestion("What is 2 + 2?", "4"));

        int chosenQuestion = menuFunctionsInstance.promptPrintQuestions(quizCatagory);

        assertEquals(0, chosenQuestion);

        // test 2
        input = "2\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        chosenQuestion = menuFunctionsInstance.promptPrintQuestions(quizCatagory);

        assertEquals(-1, chosenQuestion);
    }
}
