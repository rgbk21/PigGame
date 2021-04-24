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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63343", allowedHeaders = "*")
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final Log LOGGER = LogFactory.getLog(GameController.class);

    @PostMapping("/start")
    public ResponseEntity<GamePlay> startNewGame(@RequestBody StartGameRequestHolder requestHolder) {
        LOGGER.info("GameController::startNewGame starts with player:" + requestHolder.getPlayer().getUserName());
        GamePlay play = gameService.createNewGame(requestHolder.getPlayer(), requestHolder.getTargetScore());
        LOGGER.info("GameController::startNewGame ends with Response::" + play.toString());
        return ResponseEntity.ok(play);
    }

    @PostMapping("/connect")
    public ResponseEntity<GamePlay> connectToExistingGame(@RequestBody JoinGameRequestHolder requestHolder) throws InvalidGameException, GameAlreadyInProgressException {
        LOGGER.info("GameController::connectToExistingGame starts with player:" + requestHolder.getPlayer().getUserName() + " :: joining game with id :: " + requestHolder.getGameId());
        GamePlay play = gameService.connectToExistingGame(requestHolder.getPlayer(), requestHolder.getGameId());
        LOGGER.info("GameController::connectToExistingGame ends::Response::" + play.toString());
        // Once P2 connects to a game we will have to notify P1 that the game is now in progress
        messagingTemplate.convertAndSend("/topic/game-progress/" + play.getGameId(), play);
        return ResponseEntity.ok(play);
    }

    @PostMapping("/connect/random")
    public ResponseEntity<List<String>> connectToRandomGame(@RequestBody Player player) throws NoExistingGamesException {
        LOGGER.info("GameController::connectToRandomGame starts with player:Request::" + player.getUserName());
        List<String> openGames = gameService.connectToRandomGame(player);
        LOGGER.info("GameController::connectToRandomGame ends with player:Response::" + openGames);
        return ResponseEntity.ok(openGames);
    }

    @PostMapping("/gameplay/roll")
    public ResponseEntity<GamePlay> rollDice(@RequestBody GamePlay gamePlay) throws NoExistingGamesException {
        LOGGER.info("GameController::rollDice:RequestJSON:: " + gamePlay.toString());
        GamePlay play = gameService.actionNewDiceRoll(gamePlay);
        LOGGER.info("GameController::rollDice:ResponseJSON:: " + play.toString());
        messagingTemplate.convertAndSend("/topic/game-progress/" + play.getGameId(), play);
        return ResponseEntity.ok(play);
    }

    @PostMapping("/gameplay/hold")
    public ResponseEntity<GamePlay> holdScore(@RequestBody GamePlay gamePlay) throws NoExistingGamesException {
        LOGGER.info("GameController::holdScore:RequestJSON:: " + gamePlay.toString());
        GamePlay play = gameService.actionHold(gamePlay);
        LOGGER.info("GameController::holdScore:ResponseJSON:: " + play.toString());
        messagingTemplate.convertAndSend("/topic/game-progress/" + play.getGameId(), play);
        return ResponseEntity.ok(play);
    }


}
