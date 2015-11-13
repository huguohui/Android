package com.tankwar.engine;

/**
 * The control subsystem.
 * @since 2015/11/10
 */
public class ControlSubsystem extends Subsystem {
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
}