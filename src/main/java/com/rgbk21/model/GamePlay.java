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
    private boolean pl1Turn;
    private boolean pl2Turn;

    public GamePlay() {
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Integer getP1PartialScore() {
        return p1PartialScore;
    }

    public void setP1PartialScore(Integer p1PartialScore) {
        this.p1PartialScore = p1PartialScore;
    }

    public Integer getP2PartialScore() {
        return p2PartialScore;
    }

    public void setP2PartialScore(Integer p2PartialScore) {
        this.p2PartialScore = p2PartialScore;
    }

    public boolean isPl1Turn() {
        return pl1Turn;
    }

    public void setPl1Turn(boolean pl1Turn) {
        this.pl1Turn = pl1Turn;
    }

    public boolean isPl2Turn() {
        return pl2Turn;
    }

    public void setPl2Turn(boolean pl2Turn) {
        this.pl2Turn = pl2Turn;
    }

    public boolean isHold() {
        return hold;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    public Integer getP1TotalScore() {
        return p1TotalScore;
    }

    public void setP1TotalScore(Integer p1TotalScore) {
        this.p1TotalScore = p1TotalScore;
    }

    public Integer getP2TotalScore() {
        return p2TotalScore;
    }

    public void setP2TotalScore(Integer p2TotalScore) {
        this.p2TotalScore = p2TotalScore;
    }

    public Integer getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(Integer targetScore) {
        this.targetScore = targetScore;
    }

    public DiceRoll getDiceRoll() {
        return diceRoll;
    }

    public void setDiceRoll(DiceRoll diceRoll) {
        this.diceRoll = diceRoll;
    }
}
