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

-- =============================================================================
-- Seed: comments
-- 50 tasks, 2 users
-- Run after users and tasks seeds
-- =============================================================================

INSERT INTO comments (task_id, author_id, content, edited, created_at, updated_at)
VALUES

-- Task 1
(1,  1, 'Picking this up today, will update by EOD.', FALSE, NOW() - INTERVAL '30 days', NULL),
(1,  2, 'Let me know if you need anything from my end.', FALSE, NOW() - INTERVAL '29 days 18 hours', NULL),

-- Task 2
(2,  2, 'Started scoping this out. Looks straightforward.', FALSE, NOW() - INTERVAL '29 days', NULL),
(2,  1, 'Agreed. I can help with the integration part.', FALSE, NOW() - INTERVAL '28 days 20 hours', NULL),
(2,  2, 'Done. Deployed to staging for review.', TRUE,  NOW() - INTERVAL '28 days', NOW() - INTERVAL '27 days 22 hours'),

-- Task 3
(3,  1, 'Blocked on a dependency. Following up with the team.', FALSE, NOW() - INTERVAL '28 days', NULL),
(3,  2, 'I can unblock this. Give me a few hours.', FALSE, NOW() - INTERVAL '27 days 12 hours', NULL),

-- Task 4
(4,  2, 'Requirements clarified with the client. Starting now.', FALSE, NOW() - INTERVAL '27 days', NULL),
(4,  1, 'Let me know when it is ready for review.', FALSE, NOW() - INTERVAL '26 days 18 hours', NULL),
(4,  2, 'Ready. PR is up.', FALSE, NOW() - INTERVAL '26 days', NULL),

-- Task 5
(5,  1, 'This one is more complex than estimated. Will need an extra day.', TRUE,  NOW() - INTERVAL '26 days', NOW() - INTERVAL '25 days 20 hours'),
(5,  2, 'No problem, updated the deadline.', FALSE, NOW() - INTERVAL '25 days 18 hours', NULL),

-- Task 6
(6,  2, 'All clear on my end. Moving forward.', FALSE, NOW() - INTERVAL '25 days', NULL),

-- Task 7
(7,  1, 'First draft done. Needs a second pair of eyes.', FALSE, NOW() - INTERVAL '24 days', NULL),
(7,  2, 'Reviewed. Left some notes in the doc.', FALSE, NOW() - INTERVAL '23 days 18 hours', NULL),
(7,  1, 'Addressed all comments. Closing this out.', FALSE, NOW() - INTERVAL '23 days', NULL),

-- Task 8
(8,  2, 'Running into an issue with the DB schema. Will investigate.', FALSE, NOW() - INTERVAL '23 days', NULL),
(8,  1, 'Could be related to the migration we ran last week. Check V4.', FALSE, NOW() - INTERVAL '22 days 20 hours', NULL),
(8,  2, 'That was it. Fixed and verified.', TRUE,  NOW() - INTERVAL '22 days', NOW() - INTERVAL '21 days 22 hours'),

-- Task 9
(9,  1, 'Straightforward task. Done in one sitting.', FALSE, NOW() - INTERVAL '22 days', NULL),

-- Task 10
(10, 2, 'Needs input from the design team before I can proceed.', FALSE, NOW() - INTERVAL '21 days', NULL),
(10, 1, 'I will chase them up.', FALSE, NOW() - INTERVAL '20 days 18 hours', NULL),
(10, 2, 'Got the assets. Back on track.', FALSE, NOW() - INTERVAL '20 days', NULL),

-- Task 11
(11, 1, 'Scope is larger than the ticket suggests. Flagging this.', TRUE,  NOW() - INTERVAL '20 days', NOW() - INTERVAL '19 days 20 hours'),
(11, 2, 'Good catch. Updated the estimate accordingly.', FALSE, NOW() - INTERVAL '19 days 18 hours', NULL),

-- Task 12
(12, 2, 'Done. All tests passing.', FALSE, NOW() - INTERVAL '19 days', NULL),

-- Task 13
(13, 1, 'Starting on this. Should be quick.', FALSE, NOW() - INTERVAL '18 days', NULL),
(13, 2, 'Let me know if you hit anything unexpected.', FALSE, NOW() - INTERVAL '17 days 20 hours', NULL),

-- Task 14
(14, 2, 'On hold until task 13 is merged.', FALSE, NOW() - INTERVAL '17 days', NULL),
(14, 1, 'Task 13 is merged. You are clear to go.', FALSE, NOW() - INTERVAL '16 days 12 hours', NULL),
(14, 2, 'On it.', FALSE, NOW() - INTERVAL '16 days', NULL),

-- Task 15
(15, 1, 'Reviewed the spec. Have a few questions, will post in Slack.', FALSE, NOW() - INTERVAL '15 days', NULL),

-- Task 16
(16, 2, 'Completed. Pushed to main.', FALSE, NOW() - INTERVAL '15 days', NULL),
(16, 1, 'Confirmed. Looks good on staging.', FALSE, NOW() - INTERVAL '14 days 18 hours', NULL),

-- Task 17
(17, 1, 'This needs a config change on the infra side first.', FALSE, NOW() - INTERVAL '14 days', NULL),
(17, 2, 'I will handle the infra part. You take the app layer.', FALSE, NOW() - INTERVAL '13 days 18 hours', NULL),
(17, 1, 'Works end to end. Done.', FALSE, NOW() - INTERVAL '13 days', NULL),

-- Task 18
(18, 2, 'Quick win. Already deployed.', FALSE, NOW() - INTERVAL '13 days', NULL),

-- Task 19
(19, 1, 'Found an edge case we did not account for. Investigating.', TRUE,  NOW() - INTERVAL '12 days', NOW() - INTERVAL '11 days 20 hours'),
(19, 2, 'Keep me posted. We may need to revisit the requirements.', FALSE, NOW() - INTERVAL '11 days 18 hours', NULL),
(19, 1, 'Edge case handled. Added a test for it as well.', FALSE, NOW() - INTERVAL '11 days', NULL),

-- Task 20
(20, 2, 'Dependencies updated. No breaking changes.', FALSE, NOW() - INTERVAL '11 days', NULL),

-- Task 21
(21, 1, 'Starting today. Estimated two days of work.', FALSE, NOW() - INTERVAL '10 days', NULL),
(21, 2, 'Sounds good. Ping me if you need a review.', FALSE, NOW() - INTERVAL '9 days 20 hours', NULL),

-- Task 22
(22, 2, 'Ran into a permissions issue on the staging environment.', FALSE, NOW() - INTERVAL '9 days', NULL),
(22, 1, 'Fixed the role config. Try again.', FALSE, NOW() - INTERVAL '8 days 18 hours', NULL),
(22, 2, 'Working now. Continuing.', FALSE, NOW() - INTERVAL '8 days', NULL),

-- Task 23
(23, 1, 'Completed ahead of schedule.', FALSE, NOW() - INTERVAL '8 days', NULL),

-- Task 24
(24, 2, 'Waiting on sign-off from the client before proceeding.', FALSE, NOW() - INTERVAL '7 days', NULL),
(24, 1, 'I will follow up with them today.', FALSE, NOW() - INTERVAL '6 days 18 hours', NULL),
(24, 2, 'Sign-off received. Starting now.', FALSE, NOW() - INTERVAL '6 days', NULL),

-- Task 25
(25, 1, 'Half done. Will finish tomorrow morning.', TRUE,  NOW() - INTERVAL '6 days', NOW() - INTERVAL '5 days 22 hours'),

-- Task 26
(26, 2, 'Done and verified in production.', FALSE, NOW() - INTERVAL '5 days', NULL),

-- Task 27
(27, 1, 'Small task. Wrapped up in an hour.', FALSE, NOW() - INTERVAL '5 days', NULL),

-- Task 28
(28, 2, 'Needs more context from the original ticket. Will ask.', FALSE, NOW() - INTERVAL '4 days', NULL),
(28, 1, 'Added more detail to the ticket. Should be clear now.', FALSE, NOW() - INTERVAL '3 days 20 hours', NULL),
(28, 2, 'Makes sense now. On it.', FALSE, NOW() - INTERVAL '3 days 18 hours', NULL),

-- Task 29
(29, 1, 'Found a regression introduced in the last release. Fixing now.', TRUE,  NOW() - INTERVAL '3 days', NOW() - INTERVAL '2 days 22 hours'),
(29, 2, 'Good catch. Let me know when the fix is ready for review.', FALSE, NOW() - INTERVAL '2 days 20 hours', NULL),
(29, 1, 'Fix is up. PR linked in the ticket.', FALSE, NOW() - INTERVAL '2 days', NULL),

-- Task 30
(30, 2, 'All done. Closing.', FALSE, NOW() - INTERVAL '2 days', NULL),

-- Task 31
(31, 1, 'In progress. About halfway through.', FALSE, NOW() - INTERVAL '1 day 18 hours', NULL),

-- Task 32
(32, 2, 'Completed the first phase. Second phase starts tomorrow.', FALSE, NOW() - INTERVAL '1 day 12 hours', NULL),

-- Task 33
(33, 1, 'Ran all integration tests. Everything green.', FALSE, NOW() - INTERVAL '1 day', NULL),

-- Task 34
(34, 2, 'Minor UI tweak needed before this can close. Almost done.', TRUE,  NOW() - INTERVAL '20 hours', NOW() - INTERVAL '18 hours'),

-- Task 35
(35, 1, 'Confirmed working on all supported browsers.', FALSE, NOW() - INTERVAL '18 hours', NULL),

-- Task 36
(36, 2, 'Done. Documentation updated as well.', FALSE, NOW() - INTERVAL '16 hours', NULL),

-- Task 37
(37, 1, 'Needs a DB index added before this performs acceptably.', FALSE, NOW() - INTERVAL '14 hours', NULL),
(37, 2, 'Index added via migration V12. Re-test when ready.', FALSE, NOW() - INTERVAL '12 hours', NULL),
(37, 1, 'Tested. Query time went from 800ms to 12ms. Closing.', FALSE, NOW() - INTERVAL '10 hours', NULL),

-- Task 38
(38, 2, 'Starting now. Should be done by end of day.', FALSE, NOW() - INTERVAL '9 hours', NULL),

-- Task 39
(39, 1, 'This overlaps with task 40. Checking with the team.', TRUE,  NOW() - INTERVAL '8 hours', NOW() - INTERVAL '7 hours'),
(39, 2, 'They are separate concerns. Safe to proceed independently.', FALSE, NOW() - INTERVAL '6 hours', NULL),

-- Task 40
(40, 2, 'Done on my end. Needs sign-off from user 1.', FALSE, NOW() - INTERVAL '5 hours', NULL),
(40, 1, 'Signed off. Merging.', FALSE, NOW() - INTERVAL '4 hours', NULL),

-- Task 41
(41, 1, 'Quick fix. Already live.', FALSE, NOW() - INTERVAL '4 hours', NULL),

-- Task 42
(42, 2, 'In review. Will be done shortly.', FALSE, NOW() - INTERVAL '3 hours', NULL),

-- Task 43
(43, 1, 'Completed. All acceptance criteria met.', FALSE, NOW() - INTERVAL '3 hours', NULL),

-- Task 44
(44, 2, 'Pushed the changes. Awaiting pipeline.', FALSE, NOW() - INTERVAL '2 hours', NULL),
(44, 1, 'Pipeline passed. Good to merge.', FALSE, NOW() - INTERVAL '90 minutes', NULL),

-- Task 45
(45, 1, 'On it. Will update shortly.', FALSE, NOW() - INTERVAL '80 minutes', NULL),

-- Task 46
(46, 2, 'Finished. Linked the PR in the ticket description.', TRUE,  NOW() - INTERVAL '70 minutes', NOW() - INTERVAL '60 minutes'),

-- Task 47
(47, 1, 'Done. Smoke tested in staging.', FALSE, NOW() - INTERVAL '55 minutes', NULL),

-- Task 48
(48, 2, 'Minor issue found during testing. Fixing now.', FALSE, NOW() - INTERVAL '45 minutes', NULL),
(48, 1, 'Seen it too. It is the null check on line 42.', FALSE, NOW() - INTERVAL '35 minutes', NULL),
(48, 2, 'Fixed. Tests updated and passing.', FALSE, NOW() - INTERVAL '25 minutes', NULL),

-- Task 49
(49, 1, 'Completed and verified end to end.', FALSE, NOW() - INTERVAL '15 minutes', NULL),

-- Task 50
(50, 2, 'Just picked this up. Will have an update within the hour.', FALSE, NOW() - INTERVAL '5 minutes', NULL),
(50, 1, 'No rush. Take your time.', FALSE, NOW() - INTERVAL '2 minutes', NULL);