CREATE TABLE hotels (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    stars INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_hotels_stars CHECK (stars BETWEEN 1 AND 5)
);

CREATE INDEX idx_hotels_name ON hotels (name);
CREATE INDEX idx_hotels_location ON hotels (country, city);
