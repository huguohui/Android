package com.tankwar.game;

import com.tankwar.animation.Explosion;
import com.tankwar.engine.Engine;
import com.tankwar.engine.GameContext;
import com.tankwar.engine.Round;
import com.tankwar.engine.animation.FrameAnimation;
import com.tankwar.engine.entity.Terrain;
import com.tankwar.engine.subsystem.WorldSubsystem;
import com.tankwar.entity.LightTank;
import com.tankwar.entity.Tank;
import com.tankwar.entity.Wall;
import com.tankwar.ui.VirtualPad;

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
        mWorldSubsystem.setWorld(new Terrain[][]{
				//{new Wall(getEngine().getGameContext(), 0, 0)}
		});

		int width = getEngine().getGameContext().getScreenWidth(),
			height = getEngine().getGameContext().getScreenHeight();

		getEngine().getGraphicsSubsystem().setXScale(width / Game.SCREEN_WIDTH);
		getEngine().getGraphicsSubsystem().setYScale(height / Game.SCREEN_HEIGHT);

        Explosion exp = new Explosion(getEngine());
        FrameAnimation fa = new FrameAnimation(getEngine());
		Tank t = null;
		VirtualPad vp = new VirtualPad();

		fa.setDescriptor(exp);
        fa.play();
        setIsStart(true);
//		getEngine().getAISubsystem().addControllable(
//				t = new LightTank(GameContext.getGameContext(), 0, 0));
		vp.setControllable(t);
		getEngine().getControlSubsystem().addTouchEventListener(vp);
		getEngine().getGraphicsSubsystem().addDrawable(vp);
    }

	public Engine getEngine() {
		return super.getEngine();
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
