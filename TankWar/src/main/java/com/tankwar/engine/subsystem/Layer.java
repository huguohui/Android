package com.tankwar.engine.subsystem;

import com.tankwar.engine.subsystem.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * A graphics layer hold some drawable objects,
 * when this layer was showed, all objects will be showed.
 * @since 2015/11/10
 */
public class Layer {
	/** All holds objects. */
	private List<Drawable> mObjects = new ArrayList<>();

    /**
     * Get all drawable objects.
     * @return All drawable objects.
     */
    public List<Drawable> getObjects() {
        return mObjects;
    }

    /**
     * Add a drawable object.
     * @param object Drawable object.
     */
    public void addObject(Drawable object) {
        mObjects.add(object);
    }
}