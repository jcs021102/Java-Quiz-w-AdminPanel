package consoleBased;
public class player {
    private int solved;
    private int lives;

    public player() {
        this.solved = 0;
        this.lives = 3;
    }

    player(int solved, int lives) {
        this.solved = solved;
        this.lives = lives;
    }

    public int getSolved() {
        return this.solved;
    }

    public void incSolved() {
        this.solved++;
    }

    public void decLives() {
        this.lives--;
    }

    public int getLives() {
        return this.lives;
    }
}
