package com.example.tennis.scoring.system.service;

import com.example.tennis.scoring.system.exception.PlayerScoreException;
import com.example.tennis.scoring.system.model.Game;
import com.example.tennis.scoring.system.model.TennisPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService gameService;


    @BeforeEach
    void init() {
        gameService = new GameService();
    }

    @DisplayName("Throws exception because Player name should only be A or B")
    @ParameterizedTest
    @CsvSource({
            "C, C should not be accepted",
            "4, 4 should not accepted",
            "AB BBA , Should not accept spaces"
    })
    void playGame_shouldOnlyContainAorB(String playerName, String message) {
        assertThrows(PlayerScoreException.class, () -> gameService.playGame(playerName), message);
    }

    @DisplayName("Player score can't be empty")
    @Test
    void playGame_doesNotAcceptNull() {
        assertThrows(PlayerScoreException.class, () -> gameService.playGame(null));
    }

    @DisplayName("Player score can't be empty")
    @Test
    void playGame_doesNotAcceptEmpty() {
        assertThrows(PlayerScoreException.class, () -> gameService.playGame("   "));
    }


    @DisplayName("Should print player A wins game")
    @ParameterizedTest
    @CsvSource({
            "ABABAA",
            "ABAAA",
            "AAABA"
    })
    void playGame_AShouldWinTheGame(String playerScores) {
        // Given
        String expected = "Player A wins the game";
        //when
        Game actual = gameService.playGame(playerScores);
        // then
        assertEquals(expected, actual.printWinner());
    }

    @DisplayName("Should print player B wins the game")
    @ParameterizedTest
    @CsvSource({
            "BBBAAB",
            "BABBB",
            "BBBAB"
    })
    void playGame_BshouldWinTheGame(String playerScores) {
        // Given
        String expected = "Player B wins the game";
        //when
        Game actual = gameService.playGame(playerScores);
        // then
        assertEquals(expected, actual.printWinner());
    }

    @DisplayName("Game should be in deuce mode")
    @ParameterizedTest
    @CsvSource({
            "AAABBB",
            "BBBAAA",
            "ABABAB"
    })
    void playGame_shouldBeInDeuceMode(String deucePlayerScores) {
        // Given
        boolean expectedDeuceMode = true;
        //when
        Game game = gameService.playGame(deucePlayerScores);
        // then
        assertEquals(expectedDeuceMode, game.isDeuceMode());

    }

    @DisplayName("Game should be in deuce mode with A as winner")
    @ParameterizedTest
    @CsvSource({
            "AAABBBAA"
    })
    void playGame_shouldBeInDeuceModeWithAasWinner(String deucePlayerScores) {
        // Given
        String expected = "Player A wins the game";
        //when
        Game game = gameService.playGame(deucePlayerScores);
        // then
        assertEquals(expected, game.printWinner());

    }



    @DisplayName("Game should be in deuce mode with B as winner")
    @ParameterizedTest
    @CsvSource({
            "BBBAAABB"
    })
    void playGame_shouldBeInDeuceModeWithBasWinner(String deucePlayerScores) {
        // Given
        String expected = "Player B wins the game";
        //when
        Game game = gameService.playGame(deucePlayerScores);
        // then
        assertEquals(expected, game.printWinner());

    }

}