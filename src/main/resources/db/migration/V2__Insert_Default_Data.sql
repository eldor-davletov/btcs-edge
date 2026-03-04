-- V2: Insert default roles, permissions, and sample users

-- Default roles
INSERT INTO roles (name, description) VALUES
    ('ADMIN',   'System administrator with full access'),
    ('USER',    'Standard user with basic access'),
    ('MANAGER', 'Manager with elevated access');

-- Default permissions
INSERT INTO permissions (name, description) VALUES
    ('USER_CREATE',           'Create user'),
    ('USER_READ',             'Read user list'),
    ('USER_UPDATE',           'Update user'),
    ('USER_DELETE',           'Delete user'),
    ('ROLE_CREATE',           'Create role'),
    ('ROLE_READ',             'Read role list'),
    ('ROLE_UPDATE',           'Update role'),
    ('ROLE_DELETE',           'Delete role'),
    ('PERMISSION_READ',       'Read permissions'),
    ('ANPR_IMAGE_UPLOAD',     'Upload ANPR camera image'),
    ('ANPR_DATA_READ',        'Read ANPR data'),
    ('WEIGHBRIDGE_SIGNAL_READ','Read weighbridge signal'),
    ('REPORT_GENERATE',       'Generate system reports'),
    ('ADMIN_PANEL_ACCESS',    'Access admin panel');

-- Assign all permissions to ADMIN role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'ADMIN';

-- Assign read permissions to USER role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'USER'
  AND p.name IN ('USER_READ', 'ROLE_READ', 'PERMISSION_READ', 'ANPR_DATA_READ', 'REPORT_GENERATE');

-- Assign manager permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'MANAGER'
  AND p.name IN ('USER_READ', 'USER_UPDATE', 'ROLE_READ', 'PERMISSION_READ',
                 'ANPR_DATA_READ', 'ANPR_IMAGE_UPLOAD', 'WEIGHBRIDGE_SIGNAL_READ',
                 'REPORT_GENERATE', 'ADMIN_PANEL_ACCESS');

-- Sample users (passwords are BCrypt-encoded, managed by DataInitializer at runtime)
-- Placeholder rows: actual users created by DataInitializer with encoded passwords
