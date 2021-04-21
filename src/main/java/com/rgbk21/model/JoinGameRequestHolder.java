package com.rgbk21.model;

public class JoinGameRequestHolder {

    private String gameId;
    private Player player;

    public String getGameId() {
        return gameId;
    }

    public JoinGameRequestHolder setGameId(String gameId) {
        this.gameId = gameId;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public JoinGameRequestHolder setPlayer(Player player) {
        this.player = player;
        return this;
    }
}
