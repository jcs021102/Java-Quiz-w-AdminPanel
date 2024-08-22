package guiBased;

import GUI.MainFrame;

class entryPoint {
    private static quiz quiz;

    // A valid argument can be made for making quiz a global variable, but security
    // was chosen over readability

    // NOTE: See the Readme.md to get details on the structure of this assignment

    public static void main(String args[]) {
        // Entry point defines the static quiz object, creates the player object and
        // starts the main menu GUI
        quiz = new quiz();
        new MainFrame(quiz, new player());
    }
}
