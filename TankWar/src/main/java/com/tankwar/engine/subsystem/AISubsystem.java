package com.tankwar.engine.subsystem;


import com.tankwar.engine.Engine;

/**
 *  The engine AI subsystem that let NPC can do something
 *  like human, the NPC(BOT) can find, attack player.
 *
 *  @since 2015/11/20
 */
public class AISubsystem extends Subsystem {
    /**
     * Construct a module object by gameContext.
     *
     * @param engine
     */
    public AISubsystem(Engine engine) {
        super(engine);
    }

    /**
     * Per loop will call this method.
     */
    @Override
    public void tick() {

    }
}