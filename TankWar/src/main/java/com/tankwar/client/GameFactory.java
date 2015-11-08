package com.tankwar.client;

import com.tankwar.engine.GameContext;

/**
 * The factory of game creation.
 * @since 2015/11/08
 */
public class GameFactory {
    /**
     * Factory instance.
     */
    private static GameFactory ourInstance = new GameFactory();


    /**
     * Private constructor.
     */
    private GameFactory() {
    }


    /**
     * Create a single play game.
     */
    public static Game createGame(Game.Option option) {
        Game game = Game.create();
        game.setOption(option);
        return game;
    }
}
