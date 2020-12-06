package model;

public class Score {
    private int game_score;

    public Score(int score) {
        game_score = score;
    }

    public void addScore() {
        game_score += 10;
    }

    public int getScore() {
        return game_score;
    }
}
