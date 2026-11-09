
CREATE TABLE IF NOT EXISTS clothes_items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    type TEXT NOT NULL,
    name TEXT NOT NULL,
    size INTEGER NOT NULL,
    price REAL NOT NULL,
    color TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    season TEXT NOT NULL,
    waist_cm INTEGER,
    denim_weight_oz INTEGER,
    sleeve_length_cm INTEGER,
    print_text TEXT
);
