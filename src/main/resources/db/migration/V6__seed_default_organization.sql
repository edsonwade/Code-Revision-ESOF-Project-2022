-- V6__seed_default_organization.sql
-- Insert default organization with ID=1 if it doesn't exist to satisfy foreign key constraints

INSERT INTO organizations (id, name, email, description, active, tenant_id, created_at, updated_at) 
VALUES (1, 'Default University', 'admin@university.default', 'Main Campus', true, 'tenant-default-1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;
