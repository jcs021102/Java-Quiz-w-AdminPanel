package tests;

import java.nio.file.Path;

import org.junit.Test;
import static org.junit.Assert.*;

import consoleBased.quizCatagory;
import consoleBased.quizQuestion;

public class quizCatagoryTest {

	@Test
	void testConstructorAndGetters() {
		Path csvPath = Path.of("sample.csv");
		int index = 1;
		quizCatagory category = new quizCatagory(csvPath, index);

		assertEquals(csvPath, category.getCsvPath());
		assertEquals(index, category.getIndex());
	}

	@Test
	void testAddAndRemoveQuizQuestion() {
		quizCatagory category = new quizCatagory(Path.of("sample.csv"), 1);
		quizQuestion question1 = new quizQuestion("What is 2 + 2?", "4");
		quizQuestion question2 = new quizQuestion("What is the capital of France?", "Paris");

		category.addQuizQuestion(question1);
		category.addQuizQuestion(question2);

		assertEquals(2, category.getQuizQuestions().size());

		category.removeQuizQuestion(0);
		assertEquals(1, category.getQuizQuestions().size());
		assertEquals(question2, category.getQuizQuestions().get(0));
	}

	@Test
	void testGetRandomQuestionOnce() {
		// Test Case 1
		quizCatagory category = new quizCatagory(Path.of("sample.csv"), 1);
		quizQuestion question1 = new quizQuestion("What is 2 + 2?", "4");

		category.addQuizQuestion(question1);

		quizQuestion randomQuestion = category.getRandomQuestionONCE();
		assertEquals(question1, randomQuestion);

		try {
			category.getRandomQuestionONCE();
			fail("Expected RuntimeException but it didn't occur.");
		} catch (RuntimeException e) {
			assertEquals("No questions available.", e.getMessage());
		}
	}

	@Test
	void testGetCsvPath() {
		quizCatagory category = new quizCatagory(Path.of("sample.csv"), 1);
		assertEquals(Path.of("sample.csv"), category.getCsvPath());

		category = new quizCatagory(Path.of("another.csv"), 2);
		assertEquals(Path.of("another.csv"), category.getCsvPath());
	}

	@Test
	void testGetQuizQuestions() {
		quizCatagory category = new quizCatagory(Path.of("sample.csv"), 1);
		quizQuestion question1 = new quizQuestion("What is 2 + 2?", "4");
		quizQuestion question2 = new quizQuestion("What is the capital of France?", "Paris");

		category.addQuizQuestion(question1);
		category.addQuizQuestion(question2);

		assertEquals(2, category.getQuizQuestions().size());
		assertTrue(category.getQuizQuestions().contains(question1));
		assertTrue(category.getQuizQuestions().contains(question2));
	}

	@Test
	void testGetIndex() {
		quizCatagory category = new quizCatagory(Path.of("sample.csv"), 1);
		assertEquals(1, category.getIndex());

		category = new quizCatagory(Path.of("another.csv"), 2);
		assertEquals(2, category.getIndex());
	}
}
