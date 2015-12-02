package com.tankwar.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.tankwar.animation.Explosion;
import com.tankwar.engine.Engine;
import com.tankwar.engine.Round;
import com.tankwar.engine.animation.FrameAnimation;
import com.tankwar.engine.subsystem.Drawable;
import com.tankwar.engine.subsystem.WorldSubsystem;
import com.tankwar.net.Downloader;
import com.tankwar.net.http.Http;
import com.tankwar.net.http.HttpDownloader;
import com.tankwar.net.http.HttpRequester;

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

        Explosion exp = new Explosion(getEngine());
        FrameAnimation fa = new FrameAnimation(getEngine());
        fa.setDescriptor(exp);
        fa.play();
        setIsStart(true);

        String str = "";
        try {
            HttpRequester r = new HttpRequester(new URL("http://www.baidu.com"), null);
            r.send();
            Downloader d = new HttpDownloader(r);
           str += d.getLength();
        }catch(Exception e) {
            str += e.getMessage();
        }

        getEngine().getGameContext().set("str", str);
        getEngine().getGraphicsSubsystem().addDrawable(new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                Paint p = new Paint();
                p.setTextSize(50);
                p.setColor(Color.WHITE);
                canvas.drawText(GameRound.this.getEngine().getGameContext().get("str").toString(),
                        1000, 500, p);
            }

            @Override
            public int getIndex() {
                return 1;
            }
        });
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
