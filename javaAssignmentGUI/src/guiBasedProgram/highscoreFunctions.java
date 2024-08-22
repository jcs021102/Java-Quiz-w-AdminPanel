package guiBased;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Comparator;

public class highscoreFunctions {
    private Object[] highscoreArray;
    private File highscoreCSV;
    private int noOfHighscores;

    // noOfHighscores is a list of how many highscores should be shown
    public highscoreFunctions() {
        noOfHighscores = 10;
        highscoreArray = new Object[noOfHighscores];
        highscoreCSV = new File("highscores.csv");
        populateArray();
    }

    // getter for the array of highscores
    public Object[] getHighscores() {
        return highscoreArray;
    }

    // an arrayList is filled with the contents of the csv
    private void populateArray() {
        String line;
        ArrayList<Object[]> arrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(highscoreCSV))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                int score = Integer.parseInt(data[1]);

                arrayList.add(new Object[] { name, score });
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error while populating array: " + e.getMessage());
        }
        trim(arrayList);
    }

    // Sorts the array, then trims all aside from the noOfHighScore, adding what
    // remain to the array
    private void trim(ArrayList<Object[]> arrayList) {
        // Sort the ArrayList based on the score in descending order
        arrayList.sort(Comparator.comparingInt(entry -> (int) ((Object[]) entry)[1]).reversed());

        // Copy the sorted entries to highscoreArray
        for (int i = 0; i < noOfHighscores; i++) {
            if (i < arrayList.size()) {
                Object[] entry = arrayList.get(i);
                highscoreArray[i] = new Object[] { entry[0], entry[1] }; // Explicitly specify the type
            } else {
                highscoreArray[i] = new Object[] { "-", 0 };
            }
        }
    }

    // a score is saved the csv
    public void saveScore(String name, int score) {
        try (FileWriter writer = new FileWriter(highscoreCSV, true)) {
            // Append data to the end of the file
            String data = name + "," + score + "\n"; // Replace with your data
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // File is cleared by truncating the file to zero length
    public void clearFile() {
        voidArray();
        try (FileWriter writer = new FileWriter(highscoreCSV)) {
            // truncate the file to zero length
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Clears the array of any values, thus flushing the system from any highscores
    private void voidArray() {
        for (int i = 0; i < highscoreArray.length; i++) {
            highscoreArray[i] = null;
        }
    }

    // DEPRICIATED: an old attempt at a bubble sort for array objects on an array
    private void sortArray() {
        Object tempObject;
        for (int i = 0; i < highscoreArray.length - 1; i++) {
            for (int j = 0; j < highscoreArray.length - i - 1; j++) {
                Object[] obj1 = (Object[]) highscoreArray[j];
                Object[] obj2 = (Object[]) highscoreArray[j + 1];
                int score1 = (int) obj1[1];
                int score2 = (int) obj2[1];
                if (score1 <= score2) {
                    tempObject = highscoreArray[j];
                    highscoreArray[j] = highscoreArray[j + 1];
                    highscoreArray[j + 1] = tempObject;
                }
            }
        }
    }
}
