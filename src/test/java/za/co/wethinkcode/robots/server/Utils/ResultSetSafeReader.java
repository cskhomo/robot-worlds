// java
package za.co.wethinkcode.robots.server.Utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Utility methods for safely reading columns from a ResultSet without throwing.
 */
public final class ResultSetSafeReader {
    public ResultSetSafeReader() { }

    public static String safeGetString(ResultSet rs, String column) {
        if (rs == null || column == null) return null;
        try {
            ResultSetMetaData md = rs.getMetaData();
            int idx = findColumnIndex(md, column);
            if (idx >= 1) return rs.getString(idx);
        } catch (Exception ignored) { }
        return null;
    }

    public static Integer safeGetIntAny(ResultSet rs, String... candidates) {
        if (rs == null || candidates == null) return null;
        try {
            ResultSetMetaData md = rs.getMetaData();
            for (String candidate : candidates) {
                Integer value = safeGetIntByName(rs, md, candidate);
                if (value != null) return value;
            }
        } catch (Exception ignored) { }
        return null;
    }

    private static Integer safeGetIntByName(ResultSet rs, ResultSetMetaData md, String candidate) throws SQLException {
        if (candidate == null || md == null) return null;
        int idx = findColumnIndex(md, candidate);
        if (idx < 1) return null;
        int v = rs.getInt(idx);
        return rs.wasNull() ? null : v;
    }

    private static int findColumnIndex(ResultSetMetaData md, String column) throws SQLException {
        if (md == null || column == null) return -1;
        int count = md.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String label = md.getColumnLabel(i);
            if (label != null && label.equalsIgnoreCase(column)) return i;
            String name = md.getColumnName(i);
            if (name != null && name.equalsIgnoreCase(column)) return i;
        }
        return -1;
    }
}
