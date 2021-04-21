package com.rgbk21.service;

import com.rgbk21.exception.GameAlreadyInProgressException;
import com.rgbk21.exception.InvalidGameException;
import com.rgbk21.exception.NoExistingGamesException;
import com.rgbk21.model.*;
import com.rgbk21.storage.GameStorage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.rgbk21.model.GameStatus.*;

@Service
public class GameService {

    public GamePlay createNewGame(Player newPlayer, Integer targetScore) {

        Game game = new Game();

        game.setGameId(UUID.randomUUID().toString())
                .setTargetScore(targetScore)
                .setPlayer1(newPlayer)
                .setGameStatus(NEW)
                .setGamePlay(new GamePlay());

        game.getGamePlay()
                .setPl1Turn(true)
                .setGameId(game.getGameId())
                .setTargetScore(targetScore)
                .setGameStatus(game.getGameStatus());

        GameStorage.getInstance().addNewGame(game);

        return game.getGamePlay();
    }

    public Game connectToExistingGame(Player player2, String gameId) throws InvalidGameException, GameAlreadyInProgressException {
        if (!GameStorage.getInstance().getAllGames().containsKey(gameId)) {
            throw new InvalidGameException("Game with provided ID does not exist");
        }

        Game game = GameStorage.getInstance().getAllGames().get(gameId);

        if (game.getPlayer2() != null) {
            throw new GameAlreadyInProgressException("Game already has two players");
        }

        game.setPlayer2(player2);
        game.setGameStatus(IN_PROGRESS);

        return game;
    }

    // TODO: What happens when no valid games are found???
    // All open games will be returned to the user. It will be upto the user to select which game they want to play
    // Once the user has selected the required game, the connectToExistingGame should be called from the front end
    public List<Game> connectToRandomGame(Player player2) throws NoExistingGamesException {
        List<Game> allOpenGames;
        allOpenGames = GameStorage.getInstance().getAllGames().values().stream()
                .filter(game -> game.getGameStatus().equals(NEW))
                .collect(Collectors.toList());

        if (allOpenGames.size() == 0) {
            throw new NoExistingGamesException("There are no open games right now");
        }
        return allOpenGames;
    }

    public GamePlay actionNewDiceRoll(GamePlay gamePlay) throws NoExistingGamesException {

        Game game = findGame(gamePlay);

        DiceRoll roll = generateDiceRoll(game);
        game.getGamePlay().setDiceRoll(roll);
        updatePlayerScore(game, roll);
        return gamePlay;
    }

    public GamePlay actionHold(GamePlay gamePlay) throws NoExistingGamesException {
        Game game = findGame(gamePlay);
        boolean isPl1Turn = game.getGamePlay().isPl1Turn();
        updateTotalScore(game, isPl1Turn);
        Player winner = checkWinner(game);
        if (winner != null) {
            game.setGameStatus(FINISHED);
            game.setWinner(winner);
        } else {
            switchActivePlayer(game);
        }
        return gamePlay;
    }

    private Player checkWinner(Game game) {
        Integer targetScore = game.getGamePlay().getTargetScore();
        if (game.getGamePlay().getP1TotalScore() >= targetScore) {
            return game.getPlayer1();
        } else if (game.getGamePlay().getP2TotalScore() >= targetScore) {
            return game.getPlayer2();
        } else {
            return null;
        }
    }

    private void updateTotalScore(Game game, boolean isPl1Turn) {
        if (isPl1Turn) {
            Integer p1PartialScore = game.getGamePlay().getP1PartialScore();
            Integer p1TotalScore = game.getGamePlay().getP1TotalScore();
            game.getGamePlay().setP1TotalScore(p1PartialScore + p1TotalScore);
        } else {
            Integer p2PartialScore = game.getGamePlay().getP2PartialScore();
            Integer p2TotalScore = game.getGamePlay().getP2TotalScore();
            game.getGamePlay().setP2TotalScore(p2PartialScore + p2TotalScore);
        }
    }

    private Game findGame(GamePlay gamePlay) throws NoExistingGamesException {
        return GameStorage.getInstance().getAllGames().values().stream()
                .filter(g -> g.getGameId().equals(gamePlay.getGameId()))
                .findFirst()
                .orElseThrow(() -> (new NoExistingGamesException("Game with provided ID does not exist")));
    }

    private void updatePlayerScore(Game game, DiceRoll roll) {

        boolean isPl1Turn = game.getGamePlay().isPl1Turn();

        if (roll.getValue().equals(1)) {
            if (isPl1Turn) {
                game.getGamePlay().setP1PartialScore(0);
            } else {
                game.getGamePlay().setP2PartialScore(0);
            }
            switchActivePlayer(game);
        } else {
            updatePartialScore(game, roll, isPl1Turn);
        }
    }

    private void updatePartialScore(Game game, DiceRoll roll, boolean isPl1Turn) {
        if (isPl1Turn) {
            Integer p1PartialScore = game.getGamePlay().getP1PartialScore();
            game.getGamePlay().setP1PartialScore(p1PartialScore + roll.getValue());
        } else {
            Integer p2PartialScore = game.getGamePlay().getP2PartialScore();
            game.getGamePlay().setP2PartialScore(p2PartialScore + roll.getValue());
        }
    }

    private void switchActivePlayer(Game game) {
        if (game.getGamePlay().isPl1Turn()) {
            game.getGamePlay().setPl1Turn(false);
            game.getGamePlay().setPl2Turn(true);
        } else {
            game.getGamePlay().setPl1Turn(true);
            game.getGamePlay().setPl2Turn(false);
        }
    }

    private DiceRoll generateDiceRoll(Game game) {
        int roll = game.getRandom().nextInt(6) + 1;
        return DiceRoll.getEnum(roll);
    }


}
