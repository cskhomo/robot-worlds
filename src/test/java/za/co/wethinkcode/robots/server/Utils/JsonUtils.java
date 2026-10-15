package za.co.wethinkcode.robots.server.Utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Utility methods for extracting fields from server JSON responses.
 */
public final class JsonUtils {

    /** Returns the "result" field from the response. */
    public static String getResult(JsonObject response) {
        return response.get("result").getAsString();
    }

    /** Returns the "data" object from the response. */
    public static JsonObject getData(JsonObject response) {
        return response.getAsJsonObject("data");
    }

    /** Returns the "objects" array from the response data. */
    public static JsonArray getObjects(JsonObject response) {
        return getData(response).getAsJsonArray("objects");
    }

    /** Returns the "direction" of an object in the world. */
    public static String getObjectDirection(JsonObject obj) {
        return obj.get("direction").getAsString();
    }

    /** Returns the "type" of an object in the world. */
    public static String getObjectType(JsonObject obj) {
        return obj.get("type").getAsString();
    }

    /** Returns the "distance" of an object in the world. */
    public static int getObjectDistance(JsonObject obj) {
        return obj.get("distance").getAsInt();
    }

    /** Returns the "message" from the response data. */
    public static String getMessage(JsonObject response) {
        return getData(response).get("message").getAsString();
    }

    /** Returns the "state" object from the response. */
    public static JsonObject getState(JsonObject response) {
        return response.getAsJsonObject("state");
    }

    /** Returns the "position" array from the response data. */
    public static JsonArray getPosition(JsonObject response) {
        return getData(response).getAsJsonArray("position");
    }

    /** Returns the "direction" from the response state. */
    public static String getDirection(JsonObject response) {
        return getState(response).get("direction").getAsString();
    }

    /** Returns the "shields" count from the response state. */
    public static int getShields(JsonObject response) {
        return getState(response).get("shields").getAsInt();
    }

    /** Returns the "shots" count from the response state. */
    public static int getShots(JsonObject response) {
        return getState(response).get("shots").getAsInt();
    }

    /** Returns the "status" from the response state. */
    public static String getStatus(JsonObject response) {
        return getState(response).get("status").getAsString();
    }
}