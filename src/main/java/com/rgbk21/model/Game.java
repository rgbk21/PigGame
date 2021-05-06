package com.rgbk21.model;

import org.springframework.http.ResponseCookie;

import java.util.Random;

public class Game {

    private String gameId;
    private Player player1;
    private Player player2;
    private GameStatus gameStatus;
    private GamePlay gamePlay;
    private DiceRoll diceRoll;
    private Player winner;
    private Random random;
    private boolean hold;
    private Integer targetScore;
    private ResponseCookie p1Cookie;
    private ResponseCookie p2Cookie;

    public Game() {
        random = new Random();
        gamePlay = new GamePlay();
    }

    public String getGameId() {
        return gameId;
    }

    public Game setGameId(String gameId) {
        this.gameId = gameId;
        return this;
    }

    public ResponseCookie getP1Cookie() {
        return p1Cookie;
    }

    public Game setP1Cookie(ResponseCookie p1Cookie) {
        this.p1Cookie = p1Cookie;
        return this;
    }

    public ResponseCookie getP2Cookie() {
        return p2Cookie;
    }

    public Game setP2Cookie(ResponseCookie p2Cookie) {
        this.p2Cookie = p2Cookie;
        return this;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Game setPlayer1(Player player1) {
        this.player1 = player1;
        return this;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Game setPlayer2(Player player2) {
        this.player2 = player2;
        return this;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Game setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        return this;
    }

    public GamePlay getGamePlay() {
        return gamePlay;
    }

    public Game setGamePlay(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
        return this;
    }

    public DiceRoll getDiceRoll() {
        return diceRoll;
    }

    public Game setDiceRoll(DiceRoll diceRoll) {
        this.diceRoll = diceRoll;
        return this;
    }

    public Player getWinner() {
        return winner;
    }

    public Game setWinner(Player winner) {
        this.winner = winner;
        return this;
    }

    public Random getRandom() {
        return random;
    }

    public Game setRandom(Random random) {
        this.random = random;
        return this;
    }

    public boolean isHold() {
        return hold;
    }

    public Game setHold(boolean hold) {
        this.hold = hold;
        return this;
    }

    public Integer getTargetScore() {
        return targetScore;
    }

    public Game setTargetScore(Integer targetScore) {
        this.targetScore = targetScore;
        return this;
    }
}
