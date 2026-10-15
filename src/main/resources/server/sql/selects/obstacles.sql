SELECT x, y, width, height, type_code
FROM obstacle_positions
WHERE world_id = (
    SELECT id
    FROM worlds
    WHERE name = ? -- world name
);