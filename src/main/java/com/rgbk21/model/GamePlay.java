package com.rgbk21.model;

import com.rgbk21.utils.ErrorInfo;
import com.rgbk21.utils.Message;

import java.util.ArrayList;
import java.util.List;

public class GamePlay {

  private boolean hold;
  private Integer p1TotalScore;
  private Integer p2TotalScore;
  private Integer p1PartialScore;
  private Integer p2PartialScore;
  private String p1UserName;
  private String p2UserName;
  private Integer targetScore;
  private DiceRoll diceRoll;
  private String gameId;
  private GameStatus gameStatus;
  private boolean pl1Turn;
  private boolean pl2Turn;
  private Player winner;
  private boolean p1ClickedHold;
  private boolean p2ClickedHold;
  private final List<ErrorInfo> errorInfoList;
  private final List<Message> messageList;

  public GamePlay() {
    errorInfoList = new ArrayList<>();
    messageList = new ArrayList<>();
  }

  public boolean isHold() {
    return hold;
  }

  public GamePlay setHold(boolean hold) {
    this.hold = hold;
    return this;
  }

  public Player getWinner() {
    return winner;
  }

  public GamePlay setWinner(Player winner) {
    this.winner = winner;
    return this;
  }

  public String getP1UserName() {
    return p1UserName;
  }

  public GamePlay setP1UserName(String p1UserName) {
    this.p1UserName = p1UserName;
    return this;
  }

  public String getP2UserName() {
    return p2UserName;
  }

  public GamePlay setP2UserName(String p2UserName) {
    this.p2UserName = p2UserName;
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

  public List<ErrorInfo> getErrorInfoList() {
    return errorInfoList;
  }

  public boolean isP1ClickedHold() {
    return p1ClickedHold;
  }

  /** Sets to true if either p1 clicked on hold and turn changed to p2, or dice rolled a 1.
   * Used to calculate flawless victory. */
  public GamePlay setP1ClickedHold(boolean p1ClickedHold) {
    this.p1ClickedHold = p1ClickedHold;
    return this;
  }

  public boolean isP2ClickedHold() {
    return p2ClickedHold;
  }

  /** Sets to true if either p2 clicked on hold and turn changed to p1, or dice rolled a 1.
   * Used to calculate flawless victory. */
  public GamePlay setP2ClickedHold(boolean p2ClickedHold) {
    this.p2ClickedHold = p2ClickedHold;
    return this;
  }

  public List<Message> getMessageList() {
    return messageList;
  }

  @Override
  public String toString() {
    return "GamePlay{" +
        "hold=" + hold +
        ", p1TotalScore=" + p1TotalScore +
        ", p2TotalScore=" + p2TotalScore +
        ", p1PartialScore=" + p1PartialScore +
        ", p2PartialScore=" + p2PartialScore +
        ", p1UserName='" + p1UserName + '\'' +
        ", p2UserName='" + p2UserName + '\'' +
        ", targetScore=" + targetScore +
        ", diceRoll=" + diceRoll +
        ", gameId='" + gameId + '\'' +
        ", gameStatus=" + gameStatus +
        ", pl1Turn=" + pl1Turn +
        ", pl2Turn=" + pl2Turn +
        ", winner=" + winner +
        ", errorInfoList=" + errorInfoList +
        '}';
  }
}
