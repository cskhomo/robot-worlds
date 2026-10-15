package za.co.wethinkcode.robots.server.model.world;

import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.geometry.Position;
import za.co.wethinkcode.robots.server.model.geometry.Rectangle;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.robot.state.Mine;
import za.co.wethinkcode.robots.server.model.world.obstacle.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the game world containing robots, obstacles, and mines.
 * Manages world state, robot interactions, and game configuration.
 */
public class World {

    private String name;

    private Dimension dimension;
    private Rectangle shape;
    private Position topLeft;
    private Position bottomRight;

    private int remainingCapacity;
    public Position freePosition;

    private final int visibility = 3;
    private final int maxAmmo = 4;
    private final int maxShields = 5;

    private final int repairTime = 3;
    private final int reloadTime = 3;
    private final int setMineTime = 3;

    private final Map<String, Robot> robots;
    private final List<Obstacle> obstacles;
    private final List<Mine> mines;

    /**
     * Creates a new world with specified dimensions.
     * Initializes with default robots, obstacles, and mines for testing.
     * 
     * @param dimension the size dimensions of the world
     */
    public World(Dimension dimension) {

        this.dimension = dimension;
        this.topLeft = new Position(-dimension.width() / 2, dimension.height() / 2);
        this.bottomRight = new Position(dimension.width() / 2, -dimension.height() / 2);
        this.shape = new Rectangle(topLeft, bottomRight);

        freePosition = new Position(0, 0);
        this.remainingCapacity = dimension.capacity();

        this.robots = new HashMap<>();
        this.obstacles = new ArrayList<>();
        this.mines = new ArrayList<>();

//
//        // Hardcoded obstacles
//        addObstacle("mountain", new Position(0, 0), new Position(1, 1));
//        addObstacle("lake", new Position(2, 2),  new Position(4, 4));
//        addObstacle("pit", new Position(5, 5), new Position(5, 5));
//
//        // Hardcoded mines
//        addMine(new Position(-1, 0));
//        addMine(new Position(1, -1));
    }

    public Dimension dimension() { return dimension; }
    public int visibility() { return visibility; }
    public int maxAmmo() { return maxAmmo; }
    public int maxShields() { return maxShields; }
    public int repairTime() { return repairTime; }
    public int reloadTime() { return reloadTime; }
    public int setMineTime() { return setMineTime; }


    public List<Robot> robots() {return new ArrayList<>(robots.values());}
    public List<Obstacle> obstacles() {return obstacles;}
    public List<Mine> mines() {return mines;}

    /**
     * Adds a robot to the world and updates remaining capacity and free position.
     *
     * @param robot the robot to add
     */
    public void addRobot(Robot robot) {
        String key = robot.name().toLowerCase();
        robots.put(key, robot);
        remainingCapacity--;
        freePosition = nextFreePosition();
    }

    /**
     * Finds a robot by name (case-insensitive).
     *
     * @param robotName the name of the robot
     * @return the robot if found, null otherwise
     */
    public Robot findRobot(String robotName) {
        return robots.get(robotName.toLowerCase());
    }

    /**
     * Removes a robot from the world and updates remaining capacity.
     *
     * @param robot the robot to remove
     */
    public void removeRobot(Robot robot) {
        robots.remove(robot.name().toLowerCase());
        remainingCapacity++;
    }

    /**
     * Disconnects and removes a robot from the world.
     * Recycles its position as the next free position.
     */
    public void disconnectRobot(String robotName) {
        Robot robot = findRobot(robotName);
        if (robot == null) return;

        robots.remove(robotName.toLowerCase());
        remainingCapacity++;

        freePosition = robot.position();
    }

    /**
     * Finds the next free position in the world.
     * Returns null if no positions are available.
     *
     * @return the next free position or null if world is full
     */
    private Position nextFreePosition() {
        if (remainingCapacity == 0) {
            return null;
        }

        Position position;
        do {
            position = dimension.randomPosition();
        } while (isRobotAt(position));

        return position;
    }

    /**
     * Checks if a robot is occupying the given position.
     *
     * @param pos the position to check
     * @return true if a robot is at the position, false otherwise
     */
    private boolean isRobotAt(Position pos) {
        for (Robot r : robots.values()) {
            if (r.position().equals(pos)) return true;
        }
        return false;
    }

    public boolean withinBounds(Position pos) {
        return shape.contains(pos);
    }


    /**
     * Renames the world.
     *
     * @param name the new name
     */
    public void rename(String name) {
        this.name = name;
    }

    /**
     * Resizes the world to new dimensions.
     *
     * @param width the new width
     * @param height the new height
     */
    public void resize(int width, int height) {
        this.dimension = new Dimension(width, height);
    }

    /**
     * Adds a mine at the specified position.
     *
     * @param position the mine's position
     */
    public void addMine(Position position) {
        mines.add(new Mine(position));
    }

    /**
     * Adds an obstacle to the world.
     *
     * @param type the type of obstacle (mountain, lake, pit)
     * @param topLeft the top-left corner of the obstacle
     * @param bottomRight the bottom-right corner of the obstacle
     * @throws IllegalArgumentException if the obstacle type is unknown
     */
    public void addObstacle(String type, Position topLeft, Position bottomRight) {
        switch (type.toLowerCase()) {
            case "mountain" -> obstacles.add(new MountainObstacle(topLeft, bottomRight));
            case "lake"     -> obstacles.add(new LakeObstacle(topLeft, bottomRight));
            case "pit"      -> obstacles.add(new PitObstacle(topLeft, bottomRight));
            default         -> throw new IllegalArgumentException("Unknown obstacle type: " + type);
        }
    }

    /**
     * Returns the name of the world.
     *
     * @return the world's name
     */
    public String getName() {
        return name;
    }


}


//    private final Position TOP_LEFT;
//    private  final  Position BOTTOM_RIGHT;//
//        this.TOP_LEFT = new Position(-WIDTH, HEIGHT);
//        this.BOTTOM_RIGHT = new Position(WIDTH, -HEIGHT);

//    /**
//     * Gets the top-left corner of the world.
//     *
//     * @return the top-left position
//     */
//    public Position getTopLeftCorner() {
//        return TOP_LEFT;
//    }
//
//    /**
//     * Gets the bottom-right corner of the world.
//     *
//     * @return the bottom-right position
//     */
//    public Position getBottomRightCorner() {
//        return BOTTOM_RIGHT;
//    }
//    /**
//     * Checks if a robot is marked as dead.
//     *
//     * @param robotName the name of the robot
//     * @return true if the robot is dead
//     */
//    public boolean isRobotDead(String robotName) {
//        for (Robot robot : this.deadRobots) {
//            if (robot.getName().equals(robotName)) return true;
//        }
//        return false;
//    }
//
//    /**
//     * Marks the specified robot as dead and removes it from the active map.
//     *
//     * @param robot the robot that has died
//     */
//    public void reportDeath(Robot robot) {
//        this.robots.remove(robot);
//        this.deadRobots.add(robot);
//    }
//
//    /**
//     * Returns a list of active robots.
//     *
//     * @return the list of robots
//     */
//

//    /**
//     * Computes the field of view for a robot.
//     *
//     * @param robot the robot
//     * @return a view containing visible objects
//     */
//    public View getFieldOfView(Robot robot) {
//        View view = new View();
//        Position origin = robot.getPosition();
//        int robotVisibility = robot.getVisibility();
//
//        for (Direction direction : Direction.values()) {
//            for (int i = 1; i <= robotVisibility; i++) {
//                Position current = calculatePosition(origin, direction, i);
//
//                if (getObjectsInDirection(view, direction, i, current)) {
//                    break;
//                }
//            }
//        }
//        return view;
//    }
//
//    private boolean getObjectsInDirection(View view, Direction direction, int distance, Position current) {
//        if (!isWithinBounds(current)) {
//            int edgeDistance = Math.max(1,distance -1);
//            view.createObject(ObjectType.EDGE, direction, edgeDistance);
//            return true;
//        }
//
//        if (isObstacleAt(current)) {
//            Obstacle obstacle = getObstacleAt(current);
//            view.createObject(ObjectType.OBSTACLE, direction, distance);
//            return !obstacle.isSeeThrough();
//        }
//
//        return false;
//    }
//
//    /**
//     * Calculates a position in the given direction from a base point.
//     *
//     * @param base the starting position
//     * @param direction the direction to move
//     * @param steps the number of steps
//     * @return the calculated position
//     */
//    public Position calculatePosition(Position base, Direction direction, int steps) {
//        int x = base.getX();
//        int y = base.getY();
//        if (direction == Direction.NORTH) return new Position(x, y - steps);
//        if (direction == Direction.SOUTH) return new Position(x, y + steps);
//        if (direction == Direction.EAST) return new Position(x + steps, y);
//        return new Position(x - steps, y);
//    }
//
//    /**
//     * Checks if a position is within the world boundaries.
//     *
//     * @param pos the position to check
//     * @return true if within bounds
//     */
//    public boolean isWithinBounds(Position pos) {
//        boolean withinTop = pos.getY() <= TOP_LEFT.getY();
//        boolean withinBottom = pos.getY() >= BOTTOM_RIGHT.getY();
//        boolean withinLeft = pos.getX() >= TOP_LEFT.getX();
//        boolean withinRight = pos.getX() <= BOTTOM_RIGHT.getX();
//        return withinTop && withinBottom && withinLeft && withinRight;
//
//    }
//
//    public EdgeDirection getEdgeDirection(Position position){
//        if (position.getX() >= BOTTOM_RIGHT.getX()) return EdgeDirection.EAST_EDGE;
//        if (position.getX() <= TOP_LEFT.getX() ) return EdgeDirection.WEST_EDGE;
//        if (position.getY() >= TOP_LEFT.getY()) return EdgeDirection.NORTH_EDGE;
//        if (position.getY() <= BOTTOM_RIGHT.getY()) return  EdgeDirection.SOUTH_EDGE;
//        return null;
//    }
//
//    /**
//     * Get an obstacle at a position.
//     */
//    private Obstacle getObstacleAt(Position pos) {
//        for (Obstacle obstacle : obstacles.keySet()) {
//            if (obstacle.isPositionInside(pos)) {
//                return obstacle;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Adds a new obstacle if the placement is valid.
//     *
//     * @param obstacle the obstacle to add
//     * @return true if successfully placed, false if invalid
//     */
//    public boolean addObstacle(Obstacle obstacle) {
//        int startX = obstacle.getTopLeftCorner().getX();
//        int startY = obstacle.getTopLeftCorner().getY();
//
//        int endX = obstacle.getBottomRightCorner().getX();
//        int endY = obstacle.getBottomRightCorner().getY();
//
//        for (int dx = startX; dx <= endX; dx++) {
//            for (int dy = startY; dy <= endY; dy++) {
//                Position position = new Position(dx, dy);
//                if (this.isObstacleAt(position)) {
//                    return false;
//                }

//                if (!this.isWithinBounds(position)) {
//                    return false;
//                }
//
//            }
//        }
//
//
//        List<Position> positions = new ArrayList<>();
//        positions.add(obstacle.getTopLeftCorner());
//        positions.add(obstacle.getBottomRightCorner());
//        this.obstacles.put(obstacle, positions);
//
//        return true;
//    }
//
//
//    /**
//     * Creates and adds a robot with the given parameters capping values that are over the maximum values of the world.
//     *
//     * @param name robot name
//     * @param robotShieldStrength robot's max shields
//     * @param robotBullets robot's bullet capacity
//     * @param robotVisibility robot's visibility
//     * @return the created robot
//     */
//    public Robot createRobot(String name, int robotShieldStrength, int robotBullets, int robotVisibility) {
//        if (robotShieldStrength > MAX_SHIELDS) {//Shield strength exceeds max allowed
//            robotShieldStrength = MAX_SHIELDS;
//        }
//        if (robotBullets > MAX_SHOTS) {//Bullet count exceeds max allowed
//            robotBullets = MAX_SHOTS;
//        }
//        if (robotVisibility > MAX_VISIBILITY) {//Visibility range exceeds max allowed
//            robotVisibility = MAX_VISIBILITY;
//        }
//
//        Robot newRobot = new Robot(name, robotShieldStrength, robotBullets, robotVisibility);
//        addRobot(newRobot);
//        return newRobot;
//    }
//
//
//    /**
//     * Attempts to move a robot to a new position.
//     *
//     * @param robot the robot to move
//     * @param newPosition the destination position
//     * @return if the move is successful, obstructed, or out-of-range.
//     */
//    public MoveResponse moveRobot(Robot robot, Position newPosition) {
//
//
//        int startX = robot.getPosition().getX();
//        int startY = robot.getPosition().getY();
//        int endX = newPosition.getX();
//        int endY = newPosition.getY();
//
//        for (int dx = startX; dx <= endX; dx++) {
//            for (int dy = startY; dy <= endY; dy++) {
//                Position position = new Position(dx, dy);
////                if (!(isWithinBounds(newPosition)) ) return MoveResponse.OUT_OF_RANGE;
//                MoveResponse result = getMovementStatus(robot, position);
//                if (result != MoveResponse.SUCCESS) return result;
//            }
//        }
//
//        robots.put(robot, newPosition);
//        return MoveResponse.SUCCESS;
//    }
//
//    private MoveResponse getMovementStatus(Robot robot, Position position){
//        if (!isWithinBounds(position)) return MoveResponse.OUT_OF_RANGE;
//
//        if (isRobotAt(position)) return MoveResponse.OBSTRUCTED;
//
//        if (isObstacleAt(position)) {
//            Obstacle obstacle = getObstacleAt(position);
//            if(obstacle.canWalkOver()) {
//                BottomLessPit.walkOver(robot);
//                removeRobot(robot);
//            }
//
//            return MoveResponse.OBSTRUCTED;
//        }
//        return MoveResponse.SUCCESS;
//    }
//
//
//    /**
//     * Processes a bullet fired in the world.
//     *
//     * @param bullet the bullet to process
//     * @return a HitInfo object detailing the hit result
//     */
//    public HitInfo handleBulletFired(Bullet bullet) {
//        Position start = bullet.startPosition();
//        Direction dir = bullet.direction();
//        int maxRange = bullet.distance();
//
//        for (int step = 1; step <= maxRange; step++) {
//            Position current = calculatePosition(start, dir, step);
//
//            if (!isWithinBounds(current)) {
//                break;
//            }
//
//            for (Robot target : robots.keySet()) {
//                if (!target.isAlive()) continue;
//                if (robots.get(target).equals(current)) {
//                    target.takeDamage();
//                    return new HitInfo(true, step, ObjectType.ROBOT, target.getName());
//                }
//            }
//
//            Obstacle obstacle = getObstacleAt(current);
//            if (obstacle != null && !obstacle.isSeeThrough()) {
//                return new HitInfo(false, 0, null, "");
//            }
//        }
//
//        return new HitInfo(false, 0, null, "");
//    }
//
//    /**
//     * Removes a robot from the world.
//     *
//     * @param robot the robot to remove
//     */
//
//    /**
//     * Returns an unmodifiable view of the robot-to-position map.
//     *
//     * @return the map of robots and their positions
//     */
//    public Map<Robot, Position> getRobotMap() {
//        return Collections.unmodifiableMap(this.robots);
//    }
//}