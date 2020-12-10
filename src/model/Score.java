package model;

public class Score {
    private int game_score;

    /**
     *
     * @param score for hit enemy
     */
    public Score(int score) {
        game_score = score;
    }

    /**
     * when bullet hits enemy, add score
     */
    public void addScore() {
        game_score += 10;
    }

    /**
     *
     * @return player's score
     */
    public int getScore() {
        return game_score;
    }
}