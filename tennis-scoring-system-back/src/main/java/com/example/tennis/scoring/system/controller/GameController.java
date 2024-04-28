package com.example.tennis.scoring.system.controller;

import com.example.tennis.scoring.system.model.Game;
import com.example.tennis.scoring.system.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/games")
public class GameController {


    private final GameService gameService;

    @GetMapping("/initialise")
    public Game initialiseGame() {
        gameService.initialiseGame();
        return gameService.game;
    }

    @GetMapping("/update/player/points")
    public Game registerWonBallForPlayer(@RequestParam String playerName) {
        gameService.ballWon(playerName);
        return gameService.game;
    }


}
