INSERT OR REPLACE INTO worlds (id, name, width, height)
VALUES (
    (SELECT id FROM worlds WHERE name = ?),
    ?, -- world name
    ?, -- world width
    ?  -- world height
);
