SELECT x, y
FROM mine_positions
WHERE world_id = (
    SELECT id
    FROM worlds
    WHERE name = ? -- world name
);