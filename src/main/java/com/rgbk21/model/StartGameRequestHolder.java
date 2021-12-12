package com.rgbk21.model;

public class StartGameRequestHolder {

  private Integer targetScore;
  private Player player;

  public Integer getTargetScore() {
    return targetScore;
  }

  public void setTargetScore(Integer targetScore) {
    this.targetScore = targetScore;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }
}
