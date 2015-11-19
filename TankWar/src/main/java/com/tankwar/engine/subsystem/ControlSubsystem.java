package com.tankwar.engine.subsystem;

import android.view.MotionEvent;
import android.view.View;

import com.tankwar.engine.Engine;

/**
 * The control subsystem.
 * @since 2015/11/10
 */
public class ControlSubsystem extends Subsystem implements View.OnTouchListener{
	/**
	 * Constructor.
	 * @param engine Engine instance.
	 */
	public ControlSubsystem(Engine engine) {
        super(engine);
    }

    /**
     * Per loop will call this method.
     */
    @Override
    public void tick() {

    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}