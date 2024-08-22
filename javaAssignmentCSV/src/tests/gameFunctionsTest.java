package tests;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import consoleBased.quiz;
import consoleBased.quizCatagory;
import consoleBased.quizQuestion;
import consoleBased.gameFunctions;
import consoleBased.player;

import static org.junit.Assert.*;

public class gameFunctionsTest {

    private quiz quizInstance;
    private gameFunctions gameFunctionsInstance;
    private player playerInstance;

    @Before
    public void setUp() {
        quizInstance = new quiz();
        gameFunctionsInstance = new gameFunctions(quizInstance);
        playerInstance = new player();
    }

    @Test
    public void testPlayGame() {
        // test 1
        String input = "roll\n1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        quizInstance.addCatagory(new quizCatagory(Path.of("category1.csv"), 0));
        quizInstance.getQuizCatagories().get(0).addQuizQuestion(new quizQuestion("What is 2 + 2?", "4"));

        gameFunctionsInstance.playGame();

        assertEquals(1, playerInstance.getSolved());

        // test 2
        input = "ff\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        gameFunctionsInstance.playGame();

        assertEquals(0, playerInstance.getSolved());
    }

    @Test
    public void testDice() {
        // test 1
        int result = gameFunctionsInstance.dice();
        assertTrue(result >= 1 && result <= 6);

        // test 2
        result = gameFunctionsInstance.dice();
        assertTrue(result >= 1 && result <= 6);
    }

    @Test
    public void testPromptDieRoll() {
        // test 1
        String input = "roll\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals("roll", gameFunctionsInstance.promptDieRoll());

        // test 2
        input = "ff\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals("ff", gameFunctionsInstance.promptDieRoll());
    }

    @Test
    public void testChooseQuestion() {
        // test 1
        String input = "roll\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        quizInstance.addCatagory(new quizCatagory(Path.of("category1.csv"), 0));
        quizInstance.getQuizCatagories().get(0).addQuizQuestion(new quizQuestion("What is 2 + 2?", "4"));

        quizQuestion chosenQuestion = gameFunctionsInstance.chooseQuestion();

        assertNotNull(chosenQuestion);

        // test 2
        input = "ff\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        chosenQuestion = gameFunctionsInstance.chooseQuestion();

        assertNull(chosenQuestion);
    }

    @Test
    public void testAnswerAndResponse() {
        // test 1
        String input = "4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        quizQuestion chosenQuestion = new quizQuestion("What is 2 + 2?", "4");
        gameFunctionsInstance.answerAndResponse(chosenQuestion);

        assertEquals(1, playerInstance.getSolved());

        // test 2
        input = "5\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        chosenQuestion = new quizQuestion("What is 2 + 2?", "4");
        gameFunctionsInstance.answerAndResponse(chosenQuestion);

        assertEquals(0, playerInstance.getSolved());
        assertEquals(2, playerInstance.getLives());
    }

    @Test
    public void testResponse() {
        // test 1
        gameFunctionsInstance.response(true);

        assertEquals(1, playerInstance.getSolved());

        // test 2
        gameFunctionsInstance.response(false);

        assertEquals(2, playerInstance.getLives());
    }
}
