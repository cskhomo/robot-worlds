package za.co.wethinkcode.robots.server.Utils.assertions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import static org.junit.jupiter.api.Assertions.*;
import static za.co.wethinkcode.robots.server.Utils.JsonUtils.*;

/**
 * Reusable JUnit assertions for checking the existence of fields in server responses.
 */
public final class ExistenceAssertions {

    private ExistenceAssertions() {}

    /** Asserts that the response contains a "state" object or not based on expected boolean. */
    public static void assertHasState(JsonObject response, boolean expected) {
        boolean exists = response.has("state");
        assertEquals(expected, exists);
    }

    /** Asserts that the shields field exists or does not exist based on expected boolean. */
    public static void assertHasShields(JsonObject response, boolean expected) {
        boolean exists = getState(response).has("shields");
        assertEquals(expected, exists);
    }

    /** Asserts that the shots field exists or does not exist based on expected boolean. */
    public static void assertHasShots(JsonObject response, boolean expected) {
        boolean exists = getState(response).has("shots");
        assertEquals(expected, exists);
    }

    /** Asserts that the response contains an "objects" array in the data. */
    public static void assertHasObjects(JsonObject response, boolean expected) {
        boolean exists = getData(response).has("objects");
        assertEquals(expected, exists);
    }

    /** Asserts that the response objects array contains (or does not contain) at least one object of the given type. */
    public static void assertHasObject(JsonObject response, String type, boolean expected) {

        JsonArray objects = getObjects(response);
        boolean found = false;

        for (int i = 0; i < objects.size(); i++) {
            JsonObject obj = objects.get(i).getAsJsonObject();
            if (type.equals(obj.get("type").getAsString())) {
                found = true;
                break;
            }
        }

        assertEquals(expected, found);
    }
}
