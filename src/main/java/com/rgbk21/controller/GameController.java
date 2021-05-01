package com.rgbk21.controller;

import com.rgbk21.exception.GameAlreadyInProgressException;
import com.rgbk21.exception.InvalidGameException;
import com.rgbk21.exception.NoExistingGamesException;
import com.rgbk21.model.*;
import com.rgbk21.service.GameService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin(
        origins = {"https://rgbk21.github.io", "http://localhost:63343"},
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.POST, RequestMethod.GET}
)
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final Log LOGGER = LogFactory.getLog(GameController.class);

    @GetMapping("/gameInfo")
    public ResponseEntity<Hello> info() {
        Hello hello = new Hello("Hello World!");
        // 202 OK
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(hello);
        // 307 Error
//        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).body(hello);
        // 400 Error
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hello);
        // 500 Error
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(hello);
    }

    @PostMapping("/start")
    public ResponseEntity<GamePlay> startNewGame(@RequestBody StartGameRequestHolder requestHolder, HttpServletResponse response) {
        LOGGER.info("GameController::startNewGame starts with player:" + requestHolder.getPlayer().getUserName());
        GamePlay play = gameService.createNewGame(requestHolder.getPlayer(), requestHolder.getTargetScore(), response);
        LOGGER.info("GameController::startNewGame ends with Response::" + play.toString());
        return ResponseEntity.ok(play);
    }

    @PostMapping("/connect")
    public ResponseEntity<GamePlay> connectToExistingGame(@RequestBody JoinGameRequestHolder requestHolder, HttpServletResponse response)
            throws InvalidGameException, GameAlreadyInProgressException {
        LOGGER.info("GameController::connectToExistingGame starts with player:" + requestHolder.getPlayer().getUserName() +
                " :: joining game with id :: " + requestHolder.getGameId());
        GamePlay play = gameService.connectToExistingGame(requestHolder.getPlayer(), requestHolder.getGameId(), response);
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
    public ResponseEntity<GamePlay> rollDice(@RequestBody GamePlay gamePlay, HttpServletRequest request, HttpServletResponse response)
            throws NoExistingGamesException {
        LOGGER.info("GameController::rollDice:RequestJSON:: " + gamePlay.toString());
        GamePlay play = gameService.actionNewDiceRoll(gamePlay, request, response);
        LOGGER.info("GameController::rollDice:ResponseJSON:: " + play.toString());
        messagingTemplate.convertAndSend("/topic/game-progress/" + play.getGameId(), play);
        return ResponseEntity.ok(play);
    }

    @PostMapping("/gameplay/hold")
    public ResponseEntity<GamePlay> holdScore(@RequestBody GamePlay gamePlay, HttpServletRequest request) throws NoExistingGamesException {
        LOGGER.info("GameController::holdScore:RequestJSON:: " + gamePlay.toString());
        GamePlay play = gameService.actionHold(gamePlay, request);
        LOGGER.info("GameController::holdScore:ResponseJSON:: " + play.toString());
        messagingTemplate.convertAndSend("/topic/game-progress/" + play.getGameId(), play);
        return ResponseEntity.ok(play);
    }


}
