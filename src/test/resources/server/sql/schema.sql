CREATE TABLE IF NOT EXISTS worlds (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE,
    width INTEGER NOT NULL CHECK (width > 0),
    height INTEGER NOT NULL CHECK (height > 0)
);

CREATE TABLE IF NOT EXISTS obstacle_types (
    code TEXT PRIMARY KEY CHECK (code IN ('M', 'L', 'P')),
    name TEXT NOT NULL CHECK (name IN ('mountain', 'lake', 'pit')),
    blocks_vision INTEGER NOT NULL CHECK (blocks_vision IN (0,1)),
    blocks_path INTEGER NOT NULL CHECK (blocks_path IN (0,1))
);

CREATE TABLE IF NOT EXISTS obstacle_positions (
    world_id INTEGER,
    x INTEGER,
    y INTEGER,
    type_code TEXT NOT NULL,
    width INTEGER NOT NULL DEFAULT 0 CHECK (width >= 0),
    height INTEGER NOT NULL DEFAULT 0 CHECK (height >= 0),
    PRIMARY KEY (world_id, x, y),
    FOREIGN KEY (world_id) REFERENCES worlds(id),
    FOREIGN KEY (type_code) REFERENCES obstacle_types(code)
);

CREATE TABLE IF NOT EXISTS mine_positions (
    world_id INTEGER,
    x INTEGER,
    y INTEGER,
    PRIMARY KEY (world_id, x, y),
    FOREIGN KEY (world_id) REFERENCES worlds(id)
);