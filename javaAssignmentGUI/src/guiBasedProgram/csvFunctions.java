package guiBased;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class csvFunctions {
    private quiz quiz;

    public csvFunctions(quiz quiz) {
        this.quiz = quiz;
        createCatagories();
    }

    // Checks the quiz_data directory specified and creates a QuizCatagory Object
    // for each found
    //
    // This allows the program to be dynamic based on how many CSVs are found in
    // the quiz_data folder
    //
    // The Catagory name is based on the file name
    private void createCatagories() {
        String folderPath = "quiz_data";
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(folderPath))) {
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    quiz.addCatagory(new quizCatagory(path.toString()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // For every created catagory, this method accesses every row and defines a
    // quizQuestion, and adds it to the respect quizCatagory
    public void loadQuizQuestions() {
        String line;
        quizQuestion q;
        for (quizCatagory quizCatagory : quiz.getQuizCatagories()) {
            try (BufferedReader br = new BufferedReader(new FileReader(quizCatagory.getCsvPath()))) {
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    String question = data[0];
                    String answer = data[1];
                    q = quiz.createQuizQuestion(question, answer);
                    quizCatagory.getQuizQuestions().add(q);
                }

            } catch (Exception e) {
                System.out.println("The error is called: " + e.getMessage());
            }
        }
    }

    // appends a single quizQuestion to a catagory based on index
    public void saveSingleQuestiontoCsv(int catagory, quizQuestion q) {
        try (FileWriter writer = new FileWriter(quiz.getQuestionCatagory(catagory).getCsvPath(), true)) {
            String row = q.getQuestion() + "," + q.getAnswer();
            writer.write(row + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // clears a CSV then saves a catagory's list of quizQuestions to the CSV File
    // functionally updates the CSV to reflect a quizCatagory Object's state
    public void saveSingleCsv(quizCatagory quizCatagory) {
        try (FileWriter writer = new FileWriter(quizCatagory.getCsvPath())) {
            for (quizQuestion quizQuestion : quizCatagory.getQuizQuestions()) {
                String row = quizQuestion.getQuestion() + "," + quizQuestion.getAnswer();
                writer.write(row + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reads a CSV, and appends all questions and answers to a quizCatagory,
    // then saves it to a CSV
    public void savePathToCSV(quizCatagory quizCatagory, Path filePath) {
        quizQuestion q;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String question = data[0];
                String answer = data[1];
                q = quiz.createQuizQuestion(question, answer);
                quizCatagory.getQuizQuestions().add(q);
            }
        } catch (Exception e) {
            System.out.println("The error is called: " + e.getMessage());
        }
        saveSingleCsv(quizCatagory);
    }
}
