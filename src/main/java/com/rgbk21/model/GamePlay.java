package com.rgbk21.model;

public class GamePlay {

    private boolean hold;
    private Integer p1TotalScore;
    private Integer p2TotalScore;
    private Integer p1PartialScore;
    private Integer p2PartialScore;
    private Integer targetScore;
    private DiceRoll diceRoll;
    private String gameId;
    private GameStatus gameStatus;
    private boolean pl1Turn;
    private boolean pl2Turn;

    public GamePlay() {
    }

    public boolean isHold() {
        return hold;
    }

    public GamePlay setHold(boolean hold) {
        this.hold = hold;
        return this;
    }

    public Integer getP1TotalScore() {
        return p1TotalScore;
    }

    public GamePlay setP1TotalScore(Integer p1TotalScore) {
        this.p1TotalScore = p1TotalScore;
        return this;
    }

    public Integer getP2TotalScore() {
        return p2TotalScore;
    }

    public GamePlay setP2TotalScore(Integer p2TotalScore) {
        this.p2TotalScore = p2TotalScore;
        return this;
    }

    public Integer getP1PartialScore() {
        return p1PartialScore;
    }

    public GamePlay setP1PartialScore(Integer p1PartialScore) {
        this.p1PartialScore = p1PartialScore;
        return this;
    }

    public Integer getP2PartialScore() {
        return p2PartialScore;
    }

    public GamePlay setP2PartialScore(Integer p2PartialScore) {
        this.p2PartialScore = p2PartialScore;
        return this;
    }

    public Integer getTargetScore() {
        return targetScore;
    }

    public GamePlay setTargetScore(Integer targetScore) {
        this.targetScore = targetScore;
        return this;
    }

    public DiceRoll getDiceRoll() {
        return diceRoll;
    }

    public GamePlay setDiceRoll(DiceRoll diceRoll) {
        this.diceRoll = diceRoll;
        return this;
    }

    public String getGameId() {
        return gameId;
    }

    public GamePlay setGameId(String gameId) {
        this.gameId = gameId;
        return this;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public GamePlay setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        return this;
    }

    public boolean isPl1Turn() {
        return pl1Turn;
    }

    public GamePlay setPl1Turn(boolean pl1Turn) {
        this.pl1Turn = pl1Turn;
        return this;
    }

    public boolean isPl2Turn() {
        return pl2Turn;
    }

    public GamePlay setPl2Turn(boolean pl2Turn) {
        this.pl2Turn = pl2Turn;
        return this;
    }
}
