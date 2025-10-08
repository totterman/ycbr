DROP TABLE IF EXISTS boat;
DROP INDEX IF EXISTS idx_boat_name;
DROP INDEX IF EXISTS idx_boat_owner;
DROP INDEX IF EXISTS idx_boat_make;
DROP INDEX IF EXISTS idx_boat_created_at;

CREATE TABLE boat (
    id SERIAL PRIMARY KEY,
    owner VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    sign VARCHAR(50),
    make VARCHAR(50),
    model VARCHAR(50),
    loa FLOAT,
    draft FLOAT,
    beam FLOAT,
    deplacement FLOAT,
    engines VARCHAR(50),
    year VARCHAR(4),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NOT NULL,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_by VARCHAR(50) NOT NULL,
    version INT NOT NULL
);

CREATE INDEX idx_boat_name ON boat(name);
CREATE INDEX idx_boat_owner ON boat(owner);
CREATE INDEX idx_boat_make ON boat(make);
CREATE INDEX idx_boat_created_at ON boat(created_at);
