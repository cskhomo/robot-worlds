package za.co.wethinkcode.robots.client.textUI;

import java.util.List;

import textui.TextUI;
import textui.element.Case;
import textui.element.Display;
import textui.element.Element;
import textui.element.FlexAlign;
import textui.element.TextElement;
import textui.element.UnsortedListElement;
import textui.exception.ChildrenNotAllowedException;
import textui.exception.ElementFullException;
import textui.exception.ValueCannotBeSetException;

/**
 * Class responsible for creating and drawing the text-based user interface
 * for the robot client, displaying robot status, fire results, and surroundings.
 */
public class TextUserInterface {
    /**
     * Creates the left part of the header with the robot's name.
     *
     * @param robotName The name of the robot.
     * @return An Element containing the robot's name.
     */
    private static Element leftHeader(String robotName) {
        return new TextElement(2, 15).setText(robotName);
    }

    /**
     * Creates the right part of the header with shield and shots information.
     *
     * @param shield The shield value of the robot.
     * @param shots The number of shots remaining.
     * @return An Element containing shield and shots information.
     * @throws ValueCannotBeSetException If a property cannot be set.
     * @throws ChildrenNotAllowedException If children cannot be added.
     * @throws ElementFullException If the element cannot accept more children.
     */
    private static Element rightHeader(int shield, int shots) throws ValueCannotBeSetException, ChildrenNotAllowedException, ElementFullException {
        Element robotInfo = new Element(2, 30).setDisplay(Display.FLEX);
        Element shieldInfo = new TextElement(2, 15).setText("Shield: " + shield);
        Element shotsInfo = new TextElement(2, 15).setText("Shots: " + shots);
        robotInfo.insertChild(shieldInfo).insertChild(shotsInfo);
        return robotInfo;
    }

    /**
     * Creates the header element with robot name on the left and shield/shots on the right.
     *
     * @param robotName The robot's name.
     * @param shield The robot's shield value.
     * @param shots The robot's shots remaining.
     * @return An Element containing the full header.
     */
    private static Element header(String robotName, int shield, int shots) {
        Element header = new Element(2, 150);

        try {
            header.setDisplay(Display.FLEX)
                .setJustifyContent(FlexAlign.SPACE_APART);

            Element leftHeader = leftHeader(robotName);
            Element rightHeader = rightHeader(shield, shots);
            
            header.insertChild(leftHeader).insertChild(rightHeader);
    
            return header;
            
        } catch (ValueCannotBeSetException | ChildrenNotAllowedException | ElementFullException e) {
            System.out.println("An error occurred: " + e.getMessage());
            System.exit(1);
        }

        return header;
    }

    /**
     * Builds the robot state display element.
     *
     * @param direction The robot's current direction.
     * @param position The robot's current position.
     * @param state The robot's current state.
     * @return An Element containing the robot state info.
     * @throws ValueCannotBeSetException If a property cannot be set.
     * @throws ChildrenNotAllowedException If children cannot be added.
     * @throws ElementFullException If the element cannot accept more children.
     */
    public static Element robotState(String direction, String position, String state) throws ValueCannotBeSetException, ChildrenNotAllowedException, ElementFullException {
        Element container = new Element(10, 56);
        Element heading = new TextElement(2, 15)
            .setWordCase(Case.UPPERCASE)
            .setText("Robot State");

        Element list = new UnsortedListElement(4, 50)
            .addItem("DIRECTION: " + direction)
            .addItem("POSITION: " + position)
            .addItem("STATE: " + state)
            .setBorder()
            .setPadding(1, 2);

        container.insertChild(heading).insertChild(list);

        return container;
    }

    /**
     * Builds the fire state display element.
     *
     * @param impact The impact result of the fire (e.g., "Hit" or "Miss").
     * @param distance Distance to the target hit.
     * @param robotName Name of the robot hit.
     * @param shield Shield value of the robot hit.
     * @return An Element containing the fire result information.
     * @throws ValueCannotBeSetException If a property cannot be set.
     * @throws ChildrenNotAllowedException If children cannot be added.
     * @throws ElementFullException If the element cannot accept more children.
     */
    public static Element fireState(String impact, int distance, String robotName, int shield) throws ValueCannotBeSetException, ChildrenNotAllowedException, ElementFullException {
        Element container = new Element(10, 56);
        Element heading = new TextElement(2, 15)
            .setWordCase(Case.UPPERCASE)
            .setText("Fire");

        Element list = new UnsortedListElement(4, 50)
            .addItem("IMPACT: " + impact)
            .addItem("DISTANCE: " + distance)
            .addItem("ROBOT: " + robotName)
            .addItem("SHIELD: " + shield)
            .setPadding(1, 2)
            .setBorder();

        container.insertChild(heading).insertChild(list);

        return container;
    }

    /**
     * Builds the main content element containing robot state and fire state side by side.
     *
     * @param direction Robot's direction.
     * @param position Robot's position.
     * @param state Robot's current state.
     * @param impact Fire impact status.
     * @param distance Distance to the target robot.
     * @param robotName Name of the target robot.
     * @param shield Shield of the target robot.
     * @return The main Element combining robot state and fire state.
     */
    public static Element mainElement(String direction, String position, String state, String impact, int distance, String robotName, int shield) {
        Element main = null;

        try {
            Element robotState = robotState(direction, position, state);
            Element fireState = fireState(impact, distance, robotName, shield);

            main = new Element(10, 150)
                .setDisplay(Display.FLEX)
                .setJustifyContent(FlexAlign.SPACE_BETWEEN)
                .setPadding(5, 0);
            main.insertChild(robotState).insertChild(fireState);
        } catch (ValueCannotBeSetException | ChildrenNotAllowedException | ElementFullException e) {
            System.out.println("An error occurred: " + e.getMessage());
            System.exit(1);
        }

        return main;
    }

    /**
     * Builds a directional view listing the items seen in a specific direction.
     *
     * @param direction The direction to look (e.g., "North").
     * @param items List of items visible in that direction.
     * @return An Element representing the direction's view.
     * @throws ChildrenNotAllowedException If children cannot be added.
     * @throws ElementFullException If the element cannot accept more children.
     */
    public static Element directionLook(String direction, List<String> items) throws ChildrenNotAllowedException, ElementFullException {
        Element container = new Element(12, 73);
        UnsortedListElement list = new UnsortedListElement(10, 70);

        for (String item : items) list.addItem(item);

        Element heading = leftHeader(direction);

        return container
            .setBorder()
            .insertChild(heading)
            .insertChild(list);
    }

    /**
     * Builds an Element containing all four direction views inline.
     *
     * @param itemsList A list containing 4 lists, one for each direction's items.
     * @return An Element representing the combined directions.
     */
    public static Element directions(List<List<String>> itemsList) {
        Element container = null;
        try {
            container = new Element(28, 150)
                .setDisplay(Display.INLINE);
        } catch (ValueCannotBeSetException e) {
            System.out.println("An error occurred: " + e.getMessage());
            System.exit(1);
        }

        System.out.println(itemsList.size());

        for (int i = 0; i < 4; i++) {
            String direction = switch (i) {
                case 0 -> "North";
                case 1 -> "East";
                case 2 -> "South";
                case 3 -> "West";
                default -> "North";
            };

            try {
                container.insertChild(directionLook(direction, itemsList.get(i)));
            } catch (ChildrenNotAllowedException | ElementFullException e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.exit(1);
            }

        }

        return container;
    }

    // Instance variables holding robot and UI state
    private final String robotName;
    private final int shield;
    private final int shots;
    private final String direction;
    private final String position;
    private final String state;
    private final List<List<String>> directionList;

    // Variables to hold fire impact details (with default values)
    private String impact = "Data unavailable";
    private int hitRobotDistance = 30;
    private String hitRobotName = "Data unavailable";
    private int hitRobotShield = -1;

    /**
     * Constructs the TextUserInterface with robot status and environment information.
     *
     * @param robotName The robot's name.
     * @param shield The robot's shield value.
     * @param shots The robot's remaining shots.
     * @param direction The robot's current direction.
     * @param position The robot's current position.
     * @param state The robot's current state.
     * @param directionList A list of lists describing surroundings in each direction.
     */
    public TextUserInterface(String robotName, int shield, int shots, String direction, String position, String state, List<List<String>> directionList) {
        this.robotName = robotName;
        this.shield = shield;
        this.shots = shots;
        this.direction = direction;
        this.position = position;
        this.state = state;
        this.directionList = directionList;
    }

    /**
     * Sets the impact result of the last fire event.
     *
     * @param impact The impact description.
     * @return This TextUserInterface for chaining.
     */
    public TextUserInterface setImpact(String impact) {
        this.impact = impact;
        return this;
    }

    /**
     * Sets the name of the robot that was hit.
     *
     * @param hitRobotName The name of the robot hit.
     * @return This TextUserInterface for chaining.
     */
    public TextUserInterface setHitRobotName(String hitRobotName) {
        this.hitRobotName = hitRobotName;
        return this;
    }

    /**
     * Sets the shield value of the robot that was hit.
     *
     * @param hitRobotShield The shield value of the robot hit.
     * @return This TextUserInterface for chaining.
     */
    public TextUserInterface setHitRobotShield(int hitRobotShield) {
        this.hitRobotShield = hitRobotShield;
        return this;
    }

    /**
     * Sets the distance to the robot that was hit.
     *
     * @param hitRobotDistance The distance to the robot hit.
     * @return This TextUserInterface for chaining.
     */
    public TextUserInterface setHitRobotDistance(int hitRobotDistance) {
        this.hitRobotDistance = hitRobotDistance;
        return this;
    }

    /**
     * Draws the complete UI by constructing all elements and rendering via TextUI.
     */
    public void draw() {
        Element body = new Element(50, 150).setBorder();

        Element header = header(this.robotName, this.shield, this.shots);
        Element main = mainElement(
            this.direction, 
            this.position, 
            this.state, 
            this.impact, 
            this.hitRobotDistance, 
            this.hitRobotName, 
            this.hitRobotShield
        );
        
        Element directionElement = directions(this.directionList);
        
        try {
            body
                .insertChild(header)
                .insertChild(main)
                .insertChild(directionElement);
        } catch (ChildrenNotAllowedException | ElementFullException e) {
            System.out.println("An error occurred: " + e.getMessage());
            System.exit(1);
        }

        new TextUI(body).draw();
    }

    /**
     * Test main method demonstrating the UI with sample data.
     *
     * @param args Not used.
     * @throws ChildrenNotAllowedException If children cannot be added.
     * @throws ElementFullException If element cannot accept more children.
     * @throws ValueCannotBeSetException If property setting fails.
     */
    public static void main(String[] args) throws ChildrenNotAllowedException, ElementFullException, ValueCannotBeSetException {
        Element body = new Element(52, 150).setBorder();

        Element header = header("X03ER", 115, 3);
        Element main = mainElement("North", "[19,-110]", "Normal", "Hit", 30, "X03ER", 2);

        
        List<String> north = List.of("MOUNTAIN (3 steps away)");
        List<String> east = List.of("ROBOT (2 steps away)");
        List<String> south = List.of("LAKE (4 steps away), EDGE (6 steps away)");
        List<String> west = List.of("EDGE (10 steps away)");
        
        List<List<String>> directionList = List.of(north, east, south, west);
        
        Element directionElement = directions(directionList);
        
        body
            .insertChild(header)
            .insertChild(main)
            .insertChild(directionElement);

        new TextUI(body).draw();
    }
}
