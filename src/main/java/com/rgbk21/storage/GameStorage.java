package com.rgbk21.storage;

import com.rgbk21.model.Game;

import java.util.HashMap;
import java.util.Map;

public class GameStorage {

  private static Map<String, Game> allGames;
  private static GameStorage instance;

  private GameStorage() {
    allGames = new HashMap<>();
  }

  public static synchronized GameStorage getInstance() {
    if (instance == null) {
      instance = new GameStorage();
    }
    return instance;
  }

  public Map<String, Game> getAllGames() {
    return allGames;
  }

  public void addNewGame(Game game) {
    allGames.put(game.getGameId(), game);
  }

}
