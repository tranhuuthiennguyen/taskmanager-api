-- Flyway reruns this file whenever its checksum changes

TRUNCATE task_tags, comments, tasks, tags, users RESTART IDENTITY CASCADE;

INSERT INTO users (username, email, password, first_name, last_name, role) VALUES
    ('user', 'user@example.com', '$2a$12$PsViJEZnrZX9Gv/ijJ.8YOpF9oKB0Xk8ggkBlw5I4loTA4/FEot3O', 'Alice', 'Johnston', 'USER'),
    ('bob',   'bob@example.com',   '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Bob', 'Ross', 'USER'),
    ('admin', 'admin@example.com', '$2a$12$eetOA3nTX6GCqT9V8lTwxuvHBkiAt9V1KM3.L31e9es/nYfwFRW1W', null, null, 'ADMIN');

INSERT INTO tasks (title, description, status, priority, due_date, owner_id) VALUES
    ('Buy groceries',           'Milk, eggs, bread',                    'TODO',        'LOW',    '2026-04-01', 1),
    ('Fix login bug',           'JWT token expiry issue',               'IN_PROGRESS', 'HIGH',   '2026-03-10', 1),
    ('Write tests',             'Unit test TaskService',                'TODO',        'MEDIUM', '2026-03-20', 2),
    ('Deploy to staging',       'Push latest build to staging server',  'TODO',        'HIGH',   '2026-03-15', 1),
    ('Update documentation',    'Swagger and README updates',           'TODO',        'LOW',    '2026-04-10', 2),
    ('Code review PR #42',      'Review authentication module PR',      'IN_PROGRESS', 'MEDIUM', '2026-03-12', 1),
    ('Database migration',      'Add indexes to tasks table',           'TODO',        'HIGH',   '2026-03-18', 2),
    ('Fix pagination bug',      'Off-by-one error on page 2',           'IN_PROGRESS', 'HIGH',   '2026-03-11', 1),
    ('Setup CI/CD pipeline',    'Configure GitHub Actions workflow',    'DONE',        'HIGH',   '2026-03-05', 2),
    ('Refactor UserService',    'Extract auth logic into AuthService',  'DONE',        'MEDIUM', '2026-03-01', 1),
    ('Add email validation',    'Custom regex for email fields',        'DONE',        'LOW',    '2026-02-28', 2),
    ('Implement JWT refresh',   'Add refresh token endpoint',           'IN_PROGRESS', 'HIGH',   '2026-03-14', 1),
    ('Write API docs',          'Document all endpoints with examples', 'TODO',        'MEDIUM', '2026-04-05', 2),
    ('Fix CORS config',         'Allow frontend origin in prod',        'DONE',        'URGENT', '2026-03-02', 1),
    ('Add rate limiting',       'Limit login attempts per IP',          'TODO',        'HIGH',   '2026-03-25', 2),
    ('Setup Flyway',            'Database versioning with migrations',  'DONE',        'MEDIUM', '2026-02-20', 1),
    ('Implement tags feature',  'CRUD for task tags',                   'IN_PROGRESS', 'MEDIUM', '2026-03-22', 2),
    ('Add comment endpoint',    'POST /tasks/{id}/comments',            'TODO',        'MEDIUM', '2026-03-28', 1),
    ('Fix N+1 query',           'Optimize tag loading in task list',    'IN_PROGRESS', 'HIGH',   '2026-03-13', 2),
    ('Add health endpoint',     'GET /health for uptime monitoring',    'DONE',        'LOW',    '2026-02-25', 1),
    ('Implement soft delete',   'Add is_deleted flag to tasks',         'TODO',        'MEDIUM', '2026-04-02', 2),
    ('Password reset flow',     'Email-based password reset',           'TODO',        'HIGH',   '2026-03-30', 1),
    ('Add logging filter',      'Correlation ID in all log lines',      'DONE',        'MEDIUM', '2026-03-03', 2),
    ('Upgrade Spring Boot',     'Migrate to Spring Boot 4.0.3',         'DONE',        'HIGH',   '2026-02-15', 1),
    ('Write integration tests', 'Test full auth flow end to end',       'TODO',        'HIGH',   '2026-04-08', 2),
    ('Add pagination to tasks', 'Limit and offset query params',        'DONE',        'MEDIUM', '2026-03-04', 1),
    ('Fix timezone issue',      'Store all timestamps as UTC',          'DONE',        'HIGH',   '2026-02-18', 2),
    ('Implement assignee field','Allow task assignment to other users', 'IN_PROGRESS', 'MEDIUM', '2026-03-16', 1),
    ('Add due date filter',     'Filter tasks by due date range',       'TODO',        'LOW',    '2026-04-12', 2),
    ('Setup Docker Compose',    'Postgres and app in containers',       'DONE',        'HIGH',   '2026-02-10', 1),
    ('Add status filter',       'Filter tasks by status query param',   'IN_PROGRESS', 'LOW',    '2026-03-19', 2),
    ('Implement GlobalHandler', 'Centralized exception handling',       'DONE',        'HIGH',   '2026-02-22', 1),
    ('Add priority filter',     'Filter tasks by priority level',       'TODO',        'LOW',    '2026-04-15', 2),
    ('Create UserResponse DTO', 'Map User model to response DTO',       'DONE',        'LOW',    '2026-02-12', 1),
    ('Fix empty list bug',      'Return empty array not null',          'DONE',        'MEDIUM', '2026-03-06', 2),
    ('Add sort by due date',    'Sort task list by due date ASC/DESC',  'TODO',        'MEDIUM', '2026-03-26', 1),
    ('Implement cancel status', 'Allow tasks to be cancelled',         'TODO',        'LOW',    '2026-04-18', 2),
    ('Add created by field',    'Track which user created each task',   'DONE',        'MEDIUM', '2026-02-14', 1),
    ('Fix 500 on empty DB',     'Handle empty result sets gracefully',  'DONE',        'HIGH',   '2026-02-16', 2),
    ('Add task count endpoint', 'GET /tasks/count grouped by status',   'TODO',        'LOW',    '2026-04-20', 1),
    ('Secure admin endpoints',  'ROLE_ADMIN check on admin routes',     'IN_PROGRESS', 'URGENT', '2026-03-17', 2),
    ('Implement user profile',  'GET and PUT /users/me endpoints',      'DONE',        'MEDIUM', '2026-03-07', 1),
    ('Add change password',     'PUT /users/me/password endpoint',      'DONE',        'MEDIUM', '2026-03-08', 2),
    ('Fix BCrypt cost factor',  'Increase rounds from 10 to 12',        'DONE',        'LOW',    '2026-02-26', 1),
    ('Add request validation',  '@Valid on all request bodies',         'DONE',        'HIGH',   '2026-02-19', 2),
    ('Implement tag colors',    'Hex color field on tags table',        'IN_PROGRESS', 'LOW',    '2026-03-21', 1),
    ('Add overdue detection',   'Flag tasks past due date',             'TODO',        'MEDIUM', '2026-03-29', 2),
    ('Write seed data script',  'Insert test data for development',     'DONE',        'LOW',    '2026-02-08', 1),
    ('Setup secret rotation',   'Rotate JWT secrets every 90 days',     'TODO',        'URGENT', '2026-04-01', 2),
    ('Performance testing',     'Load test with 1000 concurrent users', 'TODO',        'HIGH',   '2026-04-25', 1);

INSERT INTO tags (name, color, created_by) VALUES
    ('work',        'F54927', 1),
    ('personal',    '01138C', 1),
    ('urgent',      'E4F4A6', 2),
    ('backend',     '2E75B6', 1),
    ('frontend',    'F59B42', 2),
    ('bug',         'DC2626', 1),
    ('feature',     '16A34A', 2),
    ('testing',     '7C3AED', 1),
    ('devops',      '0891B2', 2),
    ('database',    'B45309', 1),
    ('security',    'BE123C', 2),
    ('refactor',    '6B7280', 1),
    ('docs',        '0D9488', 2),
    ('performance', 'EA580C', 1),
    ('blocked',     '9F1239', 2);

INSERT INTO task_tags (task_id, tag_id) VALUES
    -- task 1: Buy groceries
    (1, 2),
    -- task 2: Fix login bug
    (2, 1), (2, 3), (2, 6), (2, 11),
    -- task 3: Write tests
    (3, 1), (3, 8),
    -- task 4: Deploy to staging
    (4, 1), (4, 9),
    -- task 5: Update documentation
    (5, 13),
    -- task 6: Code review PR #42
    (6, 1), (6, 4),
    -- task 7: Database migration
    (7, 10), (7, 9),
    -- task 8: Fix pagination bug
    (8, 6), (8, 4),
    -- task 9: Setup CI/CD pipeline
    (9, 9), (9, 1),
    -- task 10: Refactor UserService
    (10, 12), (10, 4),
    -- task 11: Add email validation
    (11, 4), (11, 11),
    -- task 12: Implement JWT refresh
    (12, 11), (12, 4), (12, 3),
    -- task 13: Write API docs
    (13, 13), (13, 1),
    -- task 14: Fix CORS config
    (14, 6), (14, 11),
    -- task 15: Add rate limiting
    (15, 11), (15, 3),
    -- task 16: Setup Flyway
    (16, 10), (16, 9),
    -- task 17: Implement tags feature
    (17, 7), (17, 4),
    -- task 18: Add comment endpoint
    (18, 7), (18, 4),
    -- task 19: Fix N+1 query
    (19, 6), (19, 14), (19, 10),
    -- task 20: Add health endpoint
    (20, 9), (20, 4),
    -- task 21: Implement soft delete
    (21, 10), (21, 4),
    -- task 22: Password reset flow
    (22, 11), (22, 7),
    -- task 23: Add logging filter
    (23, 9), (23, 4),
    -- task 24: Upgrade Spring Boot
    (24, 1), (24, 9),
    -- task 25: Write integration tests
    (25, 8), (25, 4),
    -- task 26: Add pagination to tasks
    (26, 7), (26, 4),
    -- task 27: Fix timezone issue
    (27, 6), (27, 10),
    -- task 28: Implement assignee field
    (28, 7), (28, 4),
    -- task 29: Add due date filter
    (29, 7), (29, 4),
    -- task 30: Setup Docker Compose
    (30, 9), (30, 1),
    -- task 31: Add status filter
    (31, 7), (31, 4),
    -- task 32: Implement GlobalHandler
    (32, 4), (32, 11),
    -- task 33: Add priority filter
    (33, 7), (33, 4),
    -- task 34: Create UserResponse DTO
    (34, 4), (34, 12),
    -- task 35: Fix empty list bug
    (35, 6), (35, 4),
    -- task 36: Add sort by due date
    (36, 7), (36, 4),
    -- task 37: Implement cancel status
    (37, 7), (37, 4),
    -- task 38: Add created by field
    (38, 10), (38, 4),
    -- task 39: Fix 500 on empty DB
    (39, 6), (39, 4),
    -- task 40: Add task count endpoint
    (40, 7), (40, 4),
    -- task 41: Secure admin endpoints
    (41, 11), (41, 3), (41, 1),
    -- task 42: Implement user profile
    (42, 7), (42, 4),
    -- task 43: Add change password
    (43, 11), (43, 4),
    -- task 44: Fix BCrypt cost factor
    (44, 11), (44, 6),
    -- task 45: Add request validation
    (45, 4), (45, 11),
    -- task 46: Implement tag colors
    (46, 7), (46, 5),
    -- task 47: Add overdue detection
    (47, 7), (47, 14),
    -- task 48: Write seed data script
    (48, 10), (48, 9),
    -- task 49: Setup secret rotation
    (49, 11), (49, 3),
    -- task 50: Performance testing
    (50, 14), (50, 1);