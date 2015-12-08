package com.tankwar.game;

import com.tankwar.engine.Engine;
import com.tankwar.engine.GameContext;
import com.tankwar.engine.Round;
import com.tankwar.engine.entity.Terrain;
import com.tankwar.engine.subsystem.WorldSubsystem;
import com.tankwar.entity.LightTank;
import com.tankwar.entity.Wall;

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
				{new Wall(getEngine().getGameContext(), 0, 0)}
		});

		int width = getEngine().getGameContext().getScreenWidth(),
			height = getEngine().getGameContext().getScreenHeight();

		getEngine().getGraphicsSubsystem().setXScale(width / Game.SCREEN_WIDTH);
		getEngine().getGraphicsSubsystem().setYScale(height / Game.SCREEN_HEIGHT);


//
//        Explosion exp = new Explosion(getEngine());
//        FrameAnimation fa = new FrameAnimation(getEngine());
//        fa.setDescriptor(exp);
//        fa.play();
        setIsStart(true);
		getEngine().getAISubsystem().addControllable(new LightTank(GameContext.getGameContext(), 1000, 500));


		getEngine().getControlSubsystem();

//
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				HttpRequester r = null;
//				try {
//					//dlsw.baidu.com/sw-search-sp/soft/bf/35013/Baidu_Setup_1745_2.1.0.1252_10000009.1446617488.exe
//					r = new HttpRequester(
//							new URL("http://0.baidu.com"), null);
//					r.send();
//
//					Downloader d = new HttpDownloader(r);
//					getEngine().getGameContext().set("d", d);
//					d.download(getEngine().getGameContext().SDCARD_ROOT + "/a.zip");
//				} catch (IOException e) {
//					Log.e(e);
//				}
//			}
//		}).start();
//
//        getEngine().getGraphicsSubsystem().addDrawable(new Drawable() {
//			@Override
//			public void draw(Canvas canvas) {
//				Paint p = new Paint();
//				p.setTextSize(50);
//				p.setColor(Color.WHITE);
//				GameContext c = getEngine().getGameContext();
//
//				try {
//					Thread.sleep(20);
//				} catch (Exception e) {
//
//				}
//
//				Downloader d = ((HttpDownloader) (c.get("d")));
//
//				if (d != null) {
//					long len = d.getLength(),
//						 downLen = d.getDownloadedLength();
//					float downPercent = Float.parseFloat(new DecimalFormat("00.0")
//							.format(((float) d.getDownloadedLength() / d.getLength()) * 100));
//
//					int width = getEngine().getGameContext().getScreenWidth(),
//						height = getEngine().getGameContext().getScreenHeight(),
//						pHeight = 50;
//					float widthPercentOne = (float) width / 100;
//
//					p.setColor(Color.GREEN);
//					p.setTextAlign(Paint.Align.CENTER);
//					canvas.drawText("Downloaded: " + downPercent + "%", width >> 1,
//							(height - pHeight - 8) >> 1, p);
//					canvas.drawRect(0, (height - pHeight - 8) >> 1, width,
//							(height - pHeight + 8 >> 1) + pHeight, p);
//					p.setColor(Color.BLUE);
//					canvas.drawRect(0, height - pHeight >> 1, widthPercentOne * downPercent,
//							(height - pHeight >> 1) + pHeight, p);
//				} else {
//					canvas.drawText("等待中...", 900, 500, p);
//				}
//			}
//
//
//			@Override
//			public int getIndex() {
//				return 1;
//			}
//		});
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
