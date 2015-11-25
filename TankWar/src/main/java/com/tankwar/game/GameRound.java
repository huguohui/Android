package com.tankwar.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.tankwar.engine.Engine;
import com.tankwar.engine.Round;
import com.tankwar.engine.subsystem.Drawable;
import com.tankwar.engine.subsystem.Sprite;
import com.tankwar.engine.subsystem.WorldSubsystem;
import com.tankwar.utils.Log;

/**
 * The game round.
 * @since 2015/11/25
 */
public class GameRound extends Round {
    /** The world subsystem. */
    private WorldSubsystem mWorldSubsystem;

    /**
     * Default constructor.
     *
     * @param engine A {@link Engine}.
     */
    public GameRound(Engine engine) {
        super(engine);
    }

    /**
     * Start a round.
     */
    @Override
    public void start() {
        mWorldSubsystem = getEngine().getWorldSubsystem();
        mWorldSubsystem.setWorld(null);
        getEngine().getGraphicsSubsystem().addDrawable(new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                canvas.drawBitmap(mWorldSubsystem.getBitmap("flag.png"), 0, 0, new Paint());
                Paint p = new Paint();
                p.setColor(Color.WHITE);
                p.setTextSize(40);
            }

            @Override
            public int getIndex() {
                return 0;
            }
        }, 1);
        setIsStart(true);
    }

    /**
     * Handler round progress.
     */
    @Override
    public void progress() {

    }

    /**
     * When round failed, to do something.
     */
    @Override
    public void failed() {

    }

    /**
     * Retry game.
     */
    @Override
    public void retry() {

    }

    /**
     * When round clear to do something.
     */
    @Override
    public void clear() {

    }

    /**
     * If this round is cleared, start a new round by this method returned.
     *
     * @return a new {@link Round}.
     */
    @Override
    public Round getNext() {
        return this;
    }

}
