package za.co.wethinkcode.robots.server.Utils.assertions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.Utils.JsonUtils;
import za.co.wethinkcode.robots.server.Utils.TestClient;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static za.co.wethinkcode.robots.server.Utils.JsonUtils.*;

/**
 * Reusable JUnit assertions for verifying server responses.
 */
public final class ResponseAssertions {

    /** Asserts that the client is connected to the server */
    public static void assertConnected(TestClient client, boolean status) {
        assertEquals(status, client.isConnected());
    }

    /** Asserts that the response result matches the expected string. */
    public static void assertResult(JsonObject response, String result) {
        assertNotNull(response.get("result"));
        assertEquals(result, getResult(response));
    }

    /** Asserts the position in the response data matches expected x and y. */
    public static void assertPosition(JsonObject response, int x, int y) {
        JsonArray pos = getPosition(response);
        assertEquals(x, pos.get(0).getAsInt());
        assertEquals(y, pos.get(1).getAsInt());
    }

    /** Asserts that the positions of two robots are different. */
    public static void assertPositionsDifferent(JsonObject response1, JsonObject response2) {
        JsonArray pos1 = JsonUtils.getPosition(response1);
        JsonArray pos2 = JsonUtils.getPosition(response2);

        int x1 = pos1.get(0).getAsInt(), y1 = pos1.get(1).getAsInt();
        int x2 = pos2.get(0).getAsInt(), y2 = pos2.get(1).getAsInt();

        boolean different = (x1 != x2) || (y1 != y2);
        assertTrue(different);
    }

    /** Asserts the direction in the response state. */
    public static void assertDirection(JsonObject response, String direction) {
        assertEquals(direction, getDirection(response));
    }

    /** Asserts the direction in the response state matches one of the allowed values. */
    public static void assertDirection(JsonObject response, String... allowed) {
        String dir = getDirection(response);
        boolean match = Arrays.asList(allowed).contains(dir);
        assertTrue(match);
    }

    /** Asserts the status in the response state. */
    public static void assertStatus(JsonObject response, String status) {
        assertEquals(status, getStatus(response));
    }

    /** Asserts the status in the response state matches one of the allowed values. */
    public static void assertStatus(JsonObject response, String... allowed) {
        String stat = getStatus(response);
        boolean match = Arrays.asList(allowed).contains(stat);
        assertTrue(match);
    }

    /** Asserts the shots count in the response state. */
    public static void assertShots(JsonObject response, int shots) {
        assertEquals(shots, getShots(response));
    }

    /** Asserts the shields count in the response state. */
    public static void assertShields(JsonObject response, int shields) {
        assertEquals(shields, getShields(response));
    }

    /** Asserts the message in the response data. */
    public static void assertMessage(JsonObject response, String message) {
        assertEquals(message, getMessage(response));
    }
}
