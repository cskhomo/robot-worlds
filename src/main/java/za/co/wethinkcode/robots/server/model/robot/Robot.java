package za.co.wethinkcode.robots.server.model.robot;

import za.co.wethinkcode.robots.server.model.geometry.Direction;
import za.co.wethinkcode.robots.server.model.geometry.Position;
import za.co.wethinkcode.robots.server.model.robot.state.Ammo;
import za.co.wethinkcode.robots.server.model.robot.state.Shield;
import za.co.wethinkcode.robots.server.model.robot.state.Status;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.object.Object;
import za.co.wethinkcode.robots.server.model.world.object.ObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a robot in the game world with position, direction, status, and combat stats.
 * Each robot has shields, ammunition, and can be controlled by players.
 */
public class Robot {

    private final String name;

    private Position position;
    private Direction direction;
    private Status status;

    private Shield shield;
    private Ammo ammo;

    List<Object> visibleObjects = new ArrayList<>();

    /**
     * Creates a new robot with default stats.
     * Starts facing north with normal status, 3 shields, and 3 ammo.
     * 
     * @param name the robot's name
     * @param position the robot's starting position
     */
    public Robot(String name, Position position, int shields, int ammo) {

        this.direction = Direction.NORTH;
        this.status = Status.NORMAL;

        this.name = name;
        this.position = position;
        this.shield = new Shield(shields);
        this.ammo = new Ammo(ammo);
    }

    public void scan(World world) {
        visibleObjects.clear();

        for (Direction dir : Direction.values()) {
            Position location = this.position;

            for (int dist = 1; dist <= world.visibility(); dist++) {
                location = location.step(dir);

                if (!world.withinBounds(location)) {
                    visibleObjects.add(new Object(ObjectType.EDGE, dir, dist));
                    break; // stop scanning in this direction
                }

//                if (world.isObstacleAt(location)) {
//                    visibleObjects.add(new Object(ObjectType.OBSTACLE, dir, dist));
//                    break; // stop scanning after hitting an obstacle
//                }
//
//                Robot other = world.isRobotAt(location);
//                if (other != null && other != this) {
//                    visibleObjects.add(new Object(ObjectType.ROBOT, dir, dist));
//                    break; // stop scanning after seeing a robot
//                }
            }
        }
    }


    public void turnLeft() {
        switch (direction) {
            case NORTH -> direction = Direction.WEST;
            case WEST  -> direction = Direction.SOUTH;
            case SOUTH -> direction = Direction.EAST;
            case EAST  -> direction = Direction.NORTH;
        }
    }

    public void turnRight() {
        switch (direction) {
            case NORTH -> direction = Direction.EAST;
            case EAST  -> direction = Direction.SOUTH;
            case SOUTH -> direction = Direction.WEST;
            case WEST  -> direction = Direction.NORTH;
        }
    }

    /**
     * Move the robot in a given direction by a number of steps.
     * If any step is out of bounds, the robot does not move.
     * moveDirection the direction to move (use robot's current direction for forward, opposite() for back)
     */
    public String move(int steps, World world, Direction moveDirection) {

        Position start = this.position;
        Position next = start;

        for (int i = 0; i < steps; i++) {
            next = next.step(moveDirection);
            if (!world.withinBounds(next)) {
                this.position = start;
                return "At the "+moveDirection+" edge";
            }
        }

        this.position = next; // successful move
        return "Done";
    }

    public String moveForward(int steps, World world) {
        return move(steps, world, this.direction);
    }

    public String moveBack(int steps, World world) {
        return move(steps, world, this.direction.opposite());
    }

    /** @return the robot's name */
    public String name()      { return name; }

    /** @return the robot's current position */
    public Position position() { return position; }
    
    /** @return the robot's current direction */
    public Direction direction() { return direction; }
    
    /** @return the robot's current status */
    public Status status() {return status;}

    public void status(Status status) {this.status = status;}

    /** @return the robot's current shield count */
    public Shield shield() { return shield; }

    /** @return the robot's current ammo count */
    public Ammo ammo() { return ammo; }

    @Override
    public String toString() {
        return " • %-9s : %-9s | %-4s | %-4s | Shields: %d | Ammo: %d"
                .formatted(name, position, status, direction, shield.current(), ammo.current());
    }

    public List<Object> objects() {
        return visibleObjects;}

}

//    private World world;
//    private boolean alive;
//    private boolean repairMode;
//    private boolean reloadMode;
//
//
//    public Robot(String name, int maxShieldStrength, int maxBullets, int visibilityRange, int reloadTime, int repairTime) {
//        this.NAME = name;
//        this.BULLETS_MAX = maxBullets;
//        this.SHIELD_STRENGTH_MAX = maxShieldStrength;
//
//        this.direction = Direction.NORTH;
//        this.bulletsAvail = this.BULLETS_MAX;
//        this.shieldStrength = this.SHIELD_STRENGTH_MAX;
//        this.alive = true;
//        this.reloadMode = false;
//        this.repairMode = false;
//    }
//
//    public void setWorld(World world) {
//        this.world = world;
//    }
//

//    public boolean isAlive() {
//        return this.alive;
//    }
//
//    public boolean isRepairMode() {
//        return this.repairMode;
//    }
//
//    public boolean isReloadMode() {
//        return this.reloadMode;
//    }
//
//    public HitInfo fire() {
//        if (this.bulletsAvail > 0) {
//            this.bulletsAvail = this.bulletsAvail - 1;
//            Bullet bullet = new Bullet(this.direction, BULLET_RANGE, this.getPosition());
//            return this.world.handleBulletFired(bullet);
//        }
//        System.out.println("Out of bullets, robot needs to reload");
//        return null;
//    }
//
//    public void takeDamage() {
//        if (this.shieldStrength == 0) {
//            this.die();
//        }
//        this.shieldStrength = this.shieldStrength - 1;
//    }
//
//    public void die() {
//        this.alive = false;
//        this.world.reportDeath(this);
//    }
//}
