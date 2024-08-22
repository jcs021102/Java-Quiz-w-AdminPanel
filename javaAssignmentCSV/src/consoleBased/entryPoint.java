package consoleBased;

class entryPoint {
    private static quiz quiz;
    private static menuFunctions m;

    public static void main(String args[]) {
        quiz = new quiz();
        m = new menuFunctions(quiz);
        m.mainPrompt();
    }

}
