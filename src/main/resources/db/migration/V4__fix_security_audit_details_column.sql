-- V4__fix_security_audit_details_column.sql
-- Fix details column from JSONB to TEXT for schema validation

ALTER TABLE security_audit_logs ALTER COLUMN details TYPE TEXT;
