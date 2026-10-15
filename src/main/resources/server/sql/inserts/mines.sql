INSERT OR REPLACE INTO mine_positions (world_id, x, y)
VALUES (
    (SELECT id FROM worlds WHERE name = ?),
    ?, -- x coordinate
    ?  -- y coordinate
);
