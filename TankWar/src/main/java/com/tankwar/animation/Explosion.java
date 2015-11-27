package com.tankwar.animation;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.tankwar.engine.Engine;
import com.tankwar.engine.GameContext;
import com.tankwar.engine.animation.Animation;
import com.tankwar.engine.subsystem.Sprite;
import com.tankwar.engine.subsystem.WorldSubsystem;
import com.tankwar.entity.Bullet;
import com.tankwar.utils.GameRes;
import com.tankwar.utils.GameSound;


/**
 * The descriptor of explosion animation.
 * @since 2015/11/27
 */
final public class Explosion extends Animation.Descriptor {
    /**
     * The constructor.
     */
	public Explosion(Engine engine) {
        super(engine);
        List list = new ArrayList();
        WorldSubsystem ws = engine.getWorldSubsystem();
        list.add(new Sprite(ws.getBitmap("explode1.png")));
        list.add(new Sprite(ws.getBitmap("explode2.png")));
        list.add(new Sprite(ws.getBitmap("explode3.png")));
        list.add(new Sprite(ws.getBitmap("explode4.png")));
        list.add(new Sprite(ws.getBitmap("explode5.png")));
        setSprites(list);
	}
}
