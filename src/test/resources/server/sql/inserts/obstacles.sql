INSERT OR REPLACE INTO obstacle_positions (world_id, x, y, type_code, width, height)
VALUES (
    (SELECT id FROM worlds WHERE name = ?),
    ?, -- x (top-left X)
    ?, -- y (top-left Y)
    ?, -- type_code (M, L, P)
    ?, -- width
    ? -- height
);
