package com.example.tennis.scoring.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Game model
 */
@Accessors(chain = true)
@AllArgsConstructor
@Getter
@Setter
public class Game implements Serializable {

    private TennisPlayer playerA;

    private TennisPlayer playerB;

    private boolean isDeuceMode;

    private boolean isGameOver;

    @Override
    public String toString() {
        if (playerA == null || playerB == null) {
            return "The game has not yet been started";
        }
        return String.format("Player A : %s / Player B : %s", playerA.getPoints(), playerB.getPoints());
    }

    public void incrementPoints(TennisPlayer tennisPlayer) {
        if (tennisPlayer != null) {
            int points = tennisPlayer.getPoints();
            if (points >= 30) {
                tennisPlayer.setPoints(points += 10);
            } else {
                tennisPlayer.setPoints(points += 15);
            }
        }
    }

    public String printWinner() {
        if (playerA == null || playerB == null) {
            return "The game has not started";
        } else if (playerA.isWinner()) {
            return String.format("Player %s wins the game", getPlayerA().getName());
        } else if (playerB.isWinner()) {
            return String.format("Player %s wins the game", getPlayerB().getName());
        } else {
            return "The game has not ended";
        }
    }
}
