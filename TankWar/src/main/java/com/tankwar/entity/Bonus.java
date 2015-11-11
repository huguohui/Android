//package com.tankwar.entity;
//
//import com.tankwar.client.GameMap;
//import com.tankwar.engine.CollisionCheckable;
//
//final public class Bonus extends Entity implements CollisionCheckable {
//
//	public final void create() {
//		x = (int) Math.abs(Math.random() * 10240 % (GameMap.MAP_WIDTH - 0));
//		y = (int) Math.abs(Math.random() * 10240 % (GameMap.MAP_HEIGHT - 0));
//	}
//
//
//	public boolean isCollisions(Entity entity/*int x1, int y1, int w1, int h1, int x2, int y2,
////		int w2, int h2*/) {
////		if (x1 >= x2 && x1 >= x2 + w2)
////			return false;
////		else if (x1 <= x2 && x1 + w1 <= x2)
////			return false;
////		else if (y1 >= y2 && y1 >= y2 + h2)
////			return false;
////		else if (y1 <= y2 && y1 + h1 <= y2)
////			return false;
//
//		return true;
//	}
//
//
//    /**
//     * Checks this object if collisded some entity.
//     *
//     * @param entity@return If collided true else false.
//     */
//    @Override
//    public boolean isCollision(Entity entity) {
//        return false;
//    }
//
//    /**
//     * Get width of entity.
//     *
//     * @return Width of entity.
//     */
//    @Override
//    public int getWidth() {
//        return width;
//    }
//
//    /**
//     * Get heigth of entity.
//     *
//     * @return Height of entity.
//     */
//    @Override
//    public int getHeight() {
//        return height;
//    }
//
//    /**
//     * Get the entity x value.
//     *
//     * @return The entity x value.
//     */
//    @Override
//    public int getX() {
//        return x;
//    }
//
//    /**
//     * Get the entity y value.
//     *
//     * @return The entity y value.
//     */
//    @Override
//    public int getY() {
//        return y;
//    }
//}
