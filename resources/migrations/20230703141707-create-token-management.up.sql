CREATE TABLE IF NOT EXISTS token_management (
    id uuid primary key,
    token TEXT NOT NULL,
    user_id uuid NOT NULL,
    refresh_token TEXT NOT NULL,
    created_at timestamp with time zone default NOW(),
    updated_at timestamp with time zone default NOW(),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
