package consoleBased;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class csvFunctions {
    private quiz quiz;

    public csvFunctions(quiz quiz) {
        this.quiz = quiz;
        createCatagories();
    }

    public void createCatagories() {
        String folderPath = "quiz_data";
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(folderPath))) {
            int index = 0;

            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    quiz.addCatagory(new quizCatagory(path, index));
                    index++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadQuizFromCSV() {
        String line;
        quizQuestion q;
        for (quizCatagory quizCatagory : quiz.getQuizCatagories()) {
            try (BufferedReader br = new BufferedReader(new FileReader(quizCatagory.getCsvPath().toFile()))) {
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

    public void saveSingleQuestiontoCsv(int catagory, quizQuestion q) {
        try (FileWriter writer = new FileWriter(quiz.getQuestionCatagory(catagory).getCsvPath().toFile(), true)) {
            String row = q.getQuestion() + "," + q.getAnswer();
            writer.write(row + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSingleCsv(quizCatagory quizCatagory) {
        try (FileWriter writer = new FileWriter(quizCatagory.getCsvPath().toFile())) {
            for (quizQuestion quizQuestion : quizCatagory.getQuizQuestions()) {
                String row = quizQuestion.getQuestion() + "," + quizQuestion.getAnswer();
                writer.write(row + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
