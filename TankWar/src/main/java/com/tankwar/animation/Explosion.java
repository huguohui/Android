package com.tankwar.animation;

import android.graphics.Bitmap;

import com.tankwar.engine.Engine;
import com.tankwar.engine.animation.Animation;
import com.tankwar.engine.subsystem.Sprite;
import com.tankwar.engine.subsystem.WorldSubsystem;
import com.tankwar.util.GameRes;

import java.util.ArrayList;
import java.util.List;

/**
 * The descriptor of explosion animation.
 *
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

		GameRes.initialize(engine.getGameContext().getResources());
		Bitmap explosions[] = GameRes.getExplode();
		for (Bitmap explosion : explosions) {
			list.add(new Sprite(explosion));
		}

		setLayerIndex(0);
		setSprites(list);
		setDistance(50);
		setDuration(2000);
		setIsLoop(true);
		setTotalFrames(explosions.length);
	}

	/**
	 * Get next frame of animation.
	 *
	 * @param index
	 * @return Next frame of animation.
	 * @index The frame index.
	 */
	@Override
	public Bitmap getNextFrame(int index) {
		return getSprites().get(index).getBitmap(0);
	}
}