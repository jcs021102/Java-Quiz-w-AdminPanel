package guiBased;

public class player {
    private int solved;
    private int lives;
    private String name;

    player() {
        reset();
    }

    // allows an admin to customise the number of lives and score of a player
    player(int solved, int lives) {
        this.solved = solved;
        this.lives = lives;
    }

    // sets the player to the default stats
    public void reset() {
        this.solved = 0;
        this.lives = 3;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSolved() {
        return this.solved;
    }

    public int getLives() {
        return this.lives;
    }

    // Increments the number of solved
    public void incSolved() {
        this.solved++;
    }

    // Decreases the number of Lives
    public void decLives() {
        this.lives--;
    }
}
