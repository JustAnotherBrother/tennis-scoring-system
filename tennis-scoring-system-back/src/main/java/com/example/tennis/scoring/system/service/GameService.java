package com.example.tennis.scoring.system.service;

import com.example.tennis.scoring.system.exception.PlayerScoreException;
import com.example.tennis.scoring.system.model.Game;
import com.example.tennis.scoring.system.model.TennisPlayer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    public static final String PLAYER_A = "A";
    public static final String PLAYER_B = "B";

    public Game game;

    public static final List<String> ALLOWED_CHARACTERS = List.of(PLAYER_A, PLAYER_B);

    public GameService() {
        initialiseGame();
    }

    public void initialiseGame() {
        TennisPlayer playerA = new TennisPlayer(PLAYER_A, 0, false);
        TennisPlayer playerB = new TennisPlayer(PLAYER_B, 0, false);
        game = new Game(playerA, playerB, false, false);
    }

    public void ballWon(String player) {
        if (game.isGameOver()) {
            System.out.println(game.printWinner());
            return;
        }
        TennisPlayer playerA = game.getPlayerA();
        TennisPlayer playerB = game.getPlayerB();

        if (StringUtils.equalsIgnoreCase(PLAYER_A, player)) {
            game.incrementPoints(playerA);
            // determine if the player, who just won the ball, won the actual game
            determineWinner(playerA, playerB);

        } else if (StringUtils.equalsIgnoreCase(PLAYER_B, player)) {
            game.incrementPoints(playerB);
            // determine if the player, who just won the ball, won the actual game
            determineWinner(playerB, playerA);
        } else {
            throw throwPlayerScoreException("Invalid player name [%s]. Only these are allowed %s", player);
        }

        if (!game.isGameOver() && !game.isDeuceMode()) {
            System.out.println(game.toString());
        }


        if (playerA.getPoints() == 40 && playerB.getPoints() == 40) {
            game.setDeuceMode(true);
        }

        if (game.isDeuceMode() &&
                Math.abs(playerA.getPoints() - playerB.getPoints()) >= 20) {
            if (playerA.getPoints() > playerB.getPoints()) {
                playerA.setWinner(true);
                game.setGameOver(true);
            } else {
                playerB.setWinner(true);
                game.setGameOver(true);
            }
        }

    }

    private void determineWinner(TennisPlayer playerWhoJustWonTheBall, TennisPlayer otherPlayer) {
        if (playerWhoJustWonTheBall.getPoints() > 40 && Math.abs(playerWhoJustWonTheBall.getPoints() - otherPlayer.getPoints()) >= 20) {
            playerWhoJustWonTheBall.setWinner(true);
            game.setGameOver(true);
            System.out.println(game.printWinner());
        }
    }

    public Game playGame(final String playerScores) {
        Optional.ofNullable(playerScores).orElseThrow(() ->
                throwPlayerScoreException("Invalid player names [%s]. Only these are allowed %s", playerScores)
        );
        List<String> points = List.of(playerScores.split(""));

        for (String point : points) {
            ballWon(point);
            if (game.isGameOver()) {
                break;
            }
        }
        return game;
    }

    private PlayerScoreException throwPlayerScoreException(String format, String playerScores) {
        return new PlayerScoreException(String.format(format,
                playerScores, GameService.ALLOWED_CHARACTERS));
    }
}
