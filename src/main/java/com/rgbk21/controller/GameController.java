package com.rgbk21.controller;

import com.rgbk21.exception.GameAlreadyInProgressException;
import com.rgbk21.exception.InvalidGameException;
import com.rgbk21.exception.NoExistingGamesException;
import com.rgbk21.model.GamePlay;
import com.rgbk21.model.JoinGameRequestHolder;
import com.rgbk21.model.Player;
import com.rgbk21.model.StartGameRequestHolder;
import com.rgbk21.service.GameService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    private static final Log LOGGER = LogFactory.getLog(GameController.class);

    @PostMapping("/start")
    public ResponseEntity<GamePlay> startNewGame(@RequestBody StartGameRequestHolder requestHolder) {
        LOGGER.info("GameController::startNewGame starts with player:" + requestHolder.getPlayer().getUserName());
        return ResponseEntity.ok(gameService.createNewGame(requestHolder.getPlayer(), requestHolder.getTargetScore()));
    }

    @PostMapping("/connect")
    public ResponseEntity<GamePlay> connectToExistingGame(@RequestBody JoinGameRequestHolder requestHolder) throws InvalidGameException, GameAlreadyInProgressException {
        LOGGER.info("GameController::connectToExistingGame starts with player:" + requestHolder.getPlayer() + " :: joining game with id :: " + requestHolder.getGameId());
        return ResponseEntity.ok(gameService.connectToExistingGame(requestHolder.getPlayer(), requestHolder.getGameId()));
    }

    @PostMapping("/connect/random")
    public ResponseEntity<List<String>> connectToRandomGame(@RequestBody Player player) throws NoExistingGamesException {
        LOGGER.info("GameController::connectToRandomGame starts with player:" + player.getUserName());
        return ResponseEntity.ok(gameService.connectToRandomGame(player));
    }

    @PostMapping("/gameplay/roll")
    public ResponseEntity<GamePlay> rollDice(@RequestBody GamePlay gamePlay) throws NoExistingGamesException {
        LOGGER.info("GameController::rollDice:" + gamePlay);
        return ResponseEntity.ok(gameService.actionNewDiceRoll(gamePlay));
    }

    @PostMapping("/gameplay/hold")
    public ResponseEntity<GamePlay> holdScore(@RequestBody GamePlay gamePlay) throws NoExistingGamesException {
        LOGGER.info("GameController::holdScore:" + gamePlay);
        return ResponseEntity.ok(gameService.actionHold(gamePlay));
    }


}
