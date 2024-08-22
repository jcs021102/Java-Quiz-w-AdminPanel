package guiBased;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sqlFunctions {
    private quiz quiz;
    //
    // database url
    //
    private String jdbcUrl = "jdbc:mysql://localhost:3306/javaAssignmentDB";
    //
    // database user's username
    //
    private String username = "root";
    //
    // database user's password
    //
    private String password = "";

    public sqlFunctions(quiz quiz) {
        this.quiz = quiz;

        createCatagories();
    }

    // creates catagories from the database
    private void createCatagories() {
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            String sql = "select q.name from quizcatagory q";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String value1 = resultSet.getString("name");

                        quiz.addCatagory(new quizCatagory(value1));
                    }
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // loads questions from the database
    public void loadQuizQuestions() {
        quizQuestion q;
        for (quizCatagory quizCatagory : quiz.getQuizCatagories()) {
            try {
                Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

                String sql = "SELECT qq.question, qq.answer " +
                        "FROM quizquestion qq " +
                        "JOIN quizcatagory qc ON qq.quizCatagoryID = qc.quizCatagoryID " +
                        "WHERE qc.name = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, quizCatagory.getCsvPath());

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        // Create a quizCatagory
                        while (resultSet.next()) {
                            String question = resultSet.getString("question");
                            String answer = resultSet.getString("answer");
                            q = quiz.createQuizQuestion(question, answer);
                            quizCatagory.getQuizQuestions().add(q);
                        }
                    }
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // appends a single quizQuestion to a catagory based on index
    public void saveSingleQuestiontoCsv(int catagory, quizQuestion q) {
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            String sql = "INSERT INTO quizquestion (question, answer, quizCatagoryID)\r\n" + //
                    "VALUES (?, ?, ?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, q.getQuestion());
                preparedStatement.setString(2, q.getAnswer());
                preparedStatement.setInt(3, catagory);

                preparedStatement.executeUpdate();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // clears a CSV then saves a catagory's list of quizQuestions to the Database
    // functionally updates the database to reflect a quizCatagory Object's state
    public void saveSingleCsv(quizCatagory quizCatagory) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            // Delete existing data from the database for the specific category
            String deleteSql = "DELETE FROM quizquestion WHERE quizCatagoryID = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.setInt(1, quiz.getQuizCatagories().indexOf(quizCatagory));
                deleteStatement.executeUpdate();
            }

            // Insert into the database
            String insertSql = "INSERT INTO quizquestion (question, answer, quizCatagoryID) VALUES (?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                for (quizQuestion quizQuestion : quizCatagory.getQuizQuestions()) {
                    insertStatement.setString(1, quizQuestion.getQuestion());
                    insertStatement.setString(2, quizQuestion.getAnswer());
                    insertStatement.setInt(3, quiz.getQuizCatagories().indexOf(quizCatagory));

                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Reads a CSV, and appends all questions and answers to a quizCatagory,
    // then saves it to the database
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
