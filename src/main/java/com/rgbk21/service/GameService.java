package com.rgbk21.service;

import com.rgbk21.exception.GameAlreadyInProgressException;
import com.rgbk21.exception.InvalidGameException;
import com.rgbk21.exception.NoExistingGamesException;
import com.rgbk21.model.*;
import com.rgbk21.storage.GameStorage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.rgbk21.model.GameStatus.*;
import static com.rgbk21.utils.Constants.*;

@Service
public class GameService {

    private static final Log LOGGER = LogFactory.getLog(GameService.class);

    public GamePlay createNewGame(Player newPlayer, Integer targetScore, HttpServletResponse response) {

        Game game = new Game();
        ResponseCookie cookie = addCookieToResponseWithName(P1_COOKIE_NAME, response);

        game.setGameId(UUID.randomUUID().toString())
                .setTargetScore(targetScore)
                .setPlayer1(newPlayer)
                .setP1Cookie(cookie)
                .setGameStatus(NEW)
                .setGamePlay(new GamePlay());

        game.getGamePlay()
                .setGameId(game.getGameId())
                .setTargetScore(targetScore)
                .setP1UserName(game.getPlayer1().getUserName())
                .setGameStatus(game.getGameStatus());

        GameStorage.getInstance().addNewGame(game);

        return game.getGamePlay();
    }

    public GamePlay connectToExistingGame(Player player2, String gameId, HttpServletResponse response)
            throws InvalidGameException, GameAlreadyInProgressException {

        if (!GameStorage.getInstance().getAllGames().containsKey(gameId)) {
            throw new InvalidGameException("Game with provided ID does not exist");
        }

        Game game = GameStorage.getInstance().getAllGames().get(gameId);

        if (game.getPlayer2() != null) {
            throw new GameAlreadyInProgressException("Game already has two players");
        }

        ResponseCookie cookie = addCookieToResponseWithName(P2_COOKIE_NAME, response);

        game.setPlayer2(player2)
                .setP2Cookie(cookie)
                .setGameStatus(IN_PROGRESS);

        game.getGamePlay()
                .setGameStatus(IN_PROGRESS)
                .setPl1Turn(true)
                .setP1TotalScore(0)
                .setP2TotalScore(0)
                .setP1PartialScore(0)
                .setP2PartialScore(0)
                .setP2UserName(game.getPlayer2().getUserName());

        return game.getGamePlay();
    }

    // TODO: What happens when no valid games are found???
    // All open games will be returned to the user. It will be upto the user to select which game they want to play
    // Once the user has selected the required game, the connectToExistingGame should be called from the front end
    public List<String> connectToRandomGame(Player player2) throws NoExistingGamesException {

        List<Game> allOpenGames;

        allOpenGames = GameStorage.getInstance().getAllGames().values().stream()
                .filter(game -> game.getGameStatus().equals(NEW))
                .collect(Collectors.toList());

        if (allOpenGames.size() == 0) {
            throw new NoExistingGamesException("There are no open games right now");
        }

        return allOpenGames.stream()
                .map(g -> g.getGameId())
                .collect(Collectors.toList());
    }

    public GamePlay actionNewDiceRoll(GamePlay gamePlay, HttpServletRequest request, HttpServletResponse response) throws NoExistingGamesException {

        LOGGER.info("GameService::actionNewDiceRoll starts::Logging headers in request");
        logHeaders(request);

        Game game = findGame(gamePlay);

        Map<String, String> playerCookies = new HashMap<>();
        playerCookies = getPlayerCookies(request, playerCookies);

        if (game.getGamePlay().isPl1Turn() && game.getP1Cookie().getValue().equals(playerCookies.get(P1_COOKIE_NAME))) {
            DiceRoll roll = generateDiceRoll(game);
            game.getGamePlay().setDiceRoll(roll);
            updatePlayerScore(game, roll);

        } else if (game.getGamePlay().isPl2Turn() && game.getP2Cookie().getValue().equals(playerCookies.get(P2_COOKIE_NAME))) {
            DiceRoll roll = generateDiceRoll(game);
            game.getGamePlay().setDiceRoll(roll);
            updatePlayerScore(game, roll);
        }

        LOGGER.info("GameService::actionNewDiceRoll ends");
        return game.getGamePlay();
    }

    public GamePlay actionHold(GamePlay gamePlay, HttpServletRequest request) throws NoExistingGamesException {

        LOGGER.info("GameService::actionHold starts::Logging headers in request");
        logHeaders(request);

        Game game = findGame(gamePlay);

        Map<String, String> playerCookies = new HashMap<>();
        playerCookies = getPlayerCookies(request, playerCookies);

        if (game.getGamePlay().isPl1Turn() && game.getP1Cookie().getValue().equals(playerCookies.get(P1_COOKIE_NAME))) {
            updateScoreAndCheckForWinner(game);
        } else if (game.getGamePlay().isPl2Turn() && game.getP2Cookie().getValue().equals(playerCookies.get(P2_COOKIE_NAME))) {
            updateScoreAndCheckForWinner(game);
        }
        return game.getGamePlay();
    }

    private void updateScoreAndCheckForWinner(Game game) {
        boolean isPl1Turn = game.getGamePlay().isPl1Turn();
        updateTotalScore(game, isPl1Turn);
        Player winner = checkWinner(game);
        if (winner != null) {
            game.setGameStatus(FINISHED).setWinner(winner);
            game.getGamePlay().setGameStatus(FINISHED).setWinner(winner);
        } else {
            switchActivePlayer(game);
        }
    }

    private void updateTotalScore(Game game, boolean isPl1Turn) {
        if (isPl1Turn) {
            Integer p1PartialScore = game.getGamePlay().getP1PartialScore();
            Integer p1TotalScore = game.getGamePlay().getP1TotalScore();
            game.getGamePlay().setP1TotalScore(p1PartialScore + p1TotalScore);
            game.getGamePlay().setP1PartialScore(0);
        } else {
            Integer p2PartialScore = game.getGamePlay().getP2PartialScore();
            Integer p2TotalScore = game.getGamePlay().getP2TotalScore();
            game.getGamePlay().setP2TotalScore(p2PartialScore + p2TotalScore);
            game.getGamePlay().setP2PartialScore(0);
        }
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

    private ResponseCookie addCookieToResponseWithName(String name, HttpServletResponse response) {

//        Cookie cookie = new Cookie(name, UUID.randomUUID().toString());
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
//        cookie.setPath("/");
////        cookie.setDomain("http://127.0.0.1");
//        response.addCookie(cookie);

        ResponseCookie resCookie = ResponseCookie.from(name, UUID.randomUUID().toString())
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .path("/")
//                .domain("localhost:63343")
                .maxAge(SEVEN_DAYS)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, resCookie.toString());

        return resCookie;
    }

    private Map<String, String> getPlayerCookies(HttpServletRequest request, Map<String, String> playerCookies) {

        Cookie[] cookies = request.getCookies();

        // Verify that the person making the move is in fact the person whose move we are waiting for
        if (cookies != null) {
            playerCookies = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals(P1_COOKIE_NAME) || c.getName().equals(P2_COOKIE_NAME))
                    .collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
        }
        return playerCookies;
    }

    private void logHeaders(HttpServletRequest request) {

        LOGGER.info("__Start of header log__");
        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                LOGGER.info("Name: " + name + ", Value:" + request.getHeader(name));
            }
        }
        LOGGER.info("__End of header log__");
    }
}
