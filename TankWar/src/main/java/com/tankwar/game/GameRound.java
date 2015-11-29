package com.tankwar.game;

import com.tankwar.animation.Explosion;
import com.tankwar.engine.Engine;
import com.tankwar.engine.Round;
import com.tankwar.engine.animation.FrameAnimation;
import com.tankwar.engine.subsystem.WorldSubsystem;

/**
 * The game round.
 * @since 2015/11/25
 */
public class GameRound extends Round {
    /** The world subsystem. */
    private WorldSubsystem mWorldSubsystem;

    /**
     * @param engine A {@link Engine}.
     * Default constructor.
     *
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

        Explosion exp = new Explosion(getEngine());
        FrameAnimation fa = new FrameAnimation(getEngine());
        fa.setDescriptor(exp);
        fa.play();
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
