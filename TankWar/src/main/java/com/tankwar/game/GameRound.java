package com.tankwar.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.tankwar.engine.Engine;
import com.tankwar.engine.Round;
import com.tankwar.engine.subsystem.Drawable;
import com.tankwar.engine.subsystem.WorldSubsystem;
import com.tankwar.net.Downloader;
import com.tankwar.net.http.HttpDownloader;
import com.tankwar.net.http.HttpRequester;
import com.tankwar.utils.Log;

import java.io.IOException;
import java.net.URL;

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
//
//        Explosion exp = new Explosion(getEngine());
//        FrameAnimation fa = new FrameAnimation(getEngine());
//        fa.setDescriptor(exp);
//        fa.play();
        setIsStart(true);


        String str = "";
        try {
			getEngine().getGameContext().set("key", "->>>>");
			new Thread(new Runnable() {
				@Override
				public void run() {
					HttpRequester r = null;
					try {
						getEngine().getGameContext().set("a", "AAAA");
						r = new HttpRequester(
								new URL("http://dx3.xiazaiba.com/Soft/S/sysdiag_3.0.0.39_XiaZaiBa.zip"), null);
						r.send();
						getEngine().getGameContext().set("b", "SEEEE");
					Log.s(getEngine().getGameContext().getMap().toString());
					Downloader d = new HttpDownloader(r);
						getEngine().getGameContext().set("d", d);
						d.download(getEngine().getGameContext().SDCARD_ROOT + "/a.zip");

						Log.s(getEngine().getGameContext().getMap().toString());
					} catch (IOException e) {
						Log.e(e);
					}
				}
			}).start();
        }catch(Exception e) {
			Log.e(e);
        }

        getEngine().getGraphicsSubsystem().addDrawable(new Drawable() {
			@Override
			public void draw(Canvas canvas) {
				Paint p = new Paint();
				p.setTextSize(50);
				p.setColor(Color.WHITE);
				try {
					Log.s(getEngine().getGameContext().getMap().toString());
					Thread.sleep(500);
				} catch (Exception e) {

				}
				if (getEngine().getGameContext().get("d") != null) {
					canvas.drawText("" + ((HttpDownloader) (GameRound.this.getEngine().getGameContext().get("d")))
									.getDownloadedLength(),
							500, 500, p);
				} else {
					canvas.drawText("IS null?" + getEngine().getGameContext().get("a") + getEngine().getGameContext().get("b"),
							(float) (500 * Math.random()), 500, p);
				}

			}


			@Override
			public int getIndex() {
				return 1;
			}
		});
    }

	public synchronized Engine getEngine() {
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
