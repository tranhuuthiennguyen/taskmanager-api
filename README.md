# Task Manager API

A RESTful backend service for managing tasks, collaborating through comments, and organising work using tags. Built with Spring Boot, secured with JWT-based authentication, and persisted using PostgreSQL via Spring JDBC.

---

## Tech Stack


| Component    | Technology                     |
| -------------- | -------------------------------- |
| Framework    | Spring Boot 4.x                |
| Security     | Spring Security + JWT          |
| Database     | PostgreSQL 17                  |
| DB Access    | Spring JDBC / JdbcTemplate     |
| Validation   | spring-boot-starter-validation |
| Build Tool   | Maven                          |
| Java Version | Java 21                        |

---

## Base URL

```
http://localhost:8080/api
```

---

## Authentication

All protected endpoints require a Bearer JWT token in the `Authorization` header:

```
Authorization: Bearer <your_jwt_token>
```

Tokens are issued on login and expire after 7 days by default.

---

## Modules

- [Auth](#auth-module)
- [Users](#user-module)
- [Tasks](#task-module)
- [Comments](#comment-module)
- [Tags](#tag-module)

---

## Auth Module

Handles user registration, login, and token management. All endpoints in this module are public.

### Endpoints


| Method | Endpoint             | Auth         | Description                     |
| -------- | ---------------------- | -------------- | --------------------------------- |
| POST   | `/api/auth/register` | Public       | Register a new user account     |
| POST   | `/api/auth/login`    | Public       | Login and receive a JWT token   |
| POST   | `/api/auth/refresh`  | Public       | Refresh an expired access token |
| POST   | `/api/auth/logout`   | Bearer Token | Invalidate the current token    |

### Register

**Request Body**


| Field     | Type   | Required | Description                         |
| ----------- | -------- | ---------- | ------------------------------------- |
| username  | String | Yes      | Unique username, 3-50 characters    |
| email     | String | Yes      | Valid email address, must be unique |
| password  | String | Yes      | Minimum 8 characters                |
| firstName | String | No       | User's first name                   |
| lastName  | String | No       | User's last name                    |

```http
POST /api/auth/register

{
  "username": "thiennth",
  "email": "thien@example.com",
  "password": "securePass123",
  "firstName": "Thien",
  "lastName": "Nguyen"
}
```

**Response — 201 Created**

```json
{
  "id": 1,
  "username": "thiennth",
  "email": "thien@example.com",
  "createdAt": "2026-03-08T04:00:00Z"
}
```

### Login

**Request Body**


| Field    | Type   | Required | Description                  |
| ---------- | -------- | ---------- | ------------------------------ |
| username | String | Yes      | Registered username or email |
| password | String | Yes      | Account password             |

```http
POST /api/auth/login

{
  "username": "thiennth",
  "password": "securePass123"
}
```

**Response — 200 OK**

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 86400
}
```

---

## User Module

Manages user profiles and account settings. Users can only modify their own profile. Admins can access all user records.

### Endpoints


| Method | Endpoint                 | Auth         | Description                            |
| -------- | -------------------------- | -------------- | ---------------------------------------- |
| GET    | `/api/users/me`          | Bearer Token | Get current authenticated user profile |
| PUT    | `/api/users/me`          | Bearer Token | Update current user's profile          |
| PUT    | `/api/users/me/password` | Bearer Token | Change current user's password         |
| DELETE | `/api/users/me`          | Bearer Token | Delete current user's account          |
| GET    | `/api/users/{id}`        | Bearer Token | Get a user by ID (admin or self)       |
| GET    | `/api/users`             | Admin Only   | List all users (paginated)             |

### User Model


| Field     | Type    | Description                |
| ----------- | --------- | ---------------------------- |
| id        | Long    | Auto-generated primary key |
| username  | String  | Unique username            |
| email     | String  | Unique email address       |
| firstName | String  | First name                 |
| lastName  | String  | Last name                  |
| role      | Enum    | `USER` or `ADMIN`          |
| createdAt | Instant | Account creation timestamp |

### Update Profile

**Request Body — PUT /api/users/me**


| Field     | Type   | Required | Description                   |
| ----------- | -------- | ---------- | ------------------------------- |
| firstName | String | No       | Updated first name            |
| lastName  | String | No       | Updated last name             |
| email     | String | No       | Updated email, must be unique |

> Password changes require a separate endpoint `PUT /api/users/me/password` with fields: `currentPassword` and `newPassword`.

---

## Task Module

Core module of the API. Tasks are owned by users and can be assigned to other users. Tasks support status tracking, due dates, priority levels, and tagging.

### Endpoints


| Method | Endpoint                       | Auth         | Description                                 |
| -------- | -------------------------------- | -------------- | --------------------------------------------- |
| GET    | `/api/tasks`                   | Bearer Token | List all tasks for current user (paginated) |
| POST   | `/api/tasks`                   | Bearer Token | Create a new task                           |
| GET    | `/api/tasks/{id}`              | Bearer Token | Get a task by ID                            |
| PUT    | `/api/tasks/{id}`              | Bearer Token | Update a task                               |
| PATCH  | `/api/tasks/{id}/status`       | Bearer Token | Update task status only                     |
| DELETE | `/api/tasks/{id}`              | Bearer Token | Delete a task                               |
| GET    | `/api/tasks/assigned`          | Bearer Token | List tasks assigned to current user         |
| POST   | `/api/tasks/{id}/tags/{tagId}` | Bearer Token | Add a tag to a task                         |
| DELETE | `/api/tasks/{id}/tags/{tagId}` | Bearer Token | Remove a tag from a task                    |

### Task Model


| Field       | Type        | Description                                |
| ------------- | ------------- | -------------------------------------------- |
| id          | Long        | Auto-generated primary key                 |
| title       | String      | Task title, max 200 characters             |
| description | String      | Detailed description, max 2000 characters  |
| status      | Enum        | `TODO`, `IN_PROGRESS`, `DONE`, `CANCELLED` |
| priority    | Enum        | `LOW`, `MEDIUM`, `HIGH`, `URGENT`          |
| dueDate     | LocalDate   | Optional deadline date                     |
| ownerId     | Long        | User ID of task creator                    |
| assigneeId  | Long        | User ID of assigned person (nullable)      |
| tags        | List\<Tag\> | Associated tags                            |
| createdAt   | Instant     | Creation timestamp                         |
| updatedAt   | Instant     | Last update timestamp                      |

### Create Task

**Request Body — POST /api/tasks**


| Field       | Type         | Required | Description                                           |
| ------------- | -------------- | ---------- | ------------------------------------------------------- |
| title       | String       | Yes      | Task title, 1-200 characters                          |
| description | String       | No       | Task description, max 2000 characters                 |
| priority    | Enum         | No       | `LOW`, `MEDIUM`, `HIGH`, `URGENT` (default: `MEDIUM`) |
| dueDate     | LocalDate    | No       | ISO date format:`yyyy-MM-dd`                          |
| assigneeId  | Long         | No       | User ID to assign the task to                         |
| tagIds      | List\<Long\> | No       | List of tag IDs to attach                             |

```http
POST /api/tasks

{
  "title": "Implement JWT authentication",
  "description": "Add JWT filter and token generation to the project",
  "priority": "HIGH",
  "dueDate": "2026-03-15",
  "assigneeId": 2,
  "tagIds": [1, 3]
}
```

### Query Parameters — GET /api/tasks


| Parameter | Type      | Required | Description                                                 |
| ----------- | ----------- | ---------- | ------------------------------------------------------------- |
| status    | Enum      | No       | Filter by status:`TODO`, `IN_PROGRESS`, `DONE`, `CANCELLED` |
| priority  | Enum      | No       | Filter by priority level                                    |
| dueBefore | LocalDate | No       | Tasks due before this date                                  |
| dueAfter  | LocalDate | No       | Tasks due after this date                                   |
| tagId     | Long      | No       | Filter tasks by tag ID                                      |
| page      | Integer   | No       | Page number, 0-indexed (default: 0)                         |
| size      | Integer   | No       | Page size (default: 20, max: 100)                           |
| sort      | String    | No       | Sort field, e.g.`createdAt,desc`                            |

---

## Comment Module

Users can leave comments on tasks. Comments are nested under tasks and belong to the user who created them. Only the comment author or an admin may delete a comment.

### Endpoints


| Method | Endpoint                            | Auth         | Description                        |
| -------- | ------------------------------------- | -------------- | ------------------------------------ |
| GET    | `/api/tasks/{taskId}/comments`      | Bearer Token | List all comments on a task        |
| POST   | `/api/tasks/{taskId}/comments`      | Bearer Token | Add a comment to a task            |
| PUT    | `/api/tasks/{taskId}/comments/{id}` | Bearer Token | Edit a comment (author only)       |
| DELETE | `/api/tasks/{taskId}/comments/{id}` | Bearer Token | Delete a comment (author or admin) |

### Comment Model


| Field          | Type    | Description                            |
| ---------------- | --------- | ---------------------------------------- |
| id             | Long    | Auto-generated primary key             |
| taskId         | Long    | Task this comment belongs to           |
| authorId       | Long    | User ID of comment author              |
| authorUsername | String  | Username of comment author             |
| content        | String  | Comment text, max 1000 characters      |
| createdAt      | Instant | Creation timestamp                     |
| updatedAt      | Instant | Last edit timestamp (null if unedited) |
| edited         | Boolean | `true` if comment has been edited      |

### Create Comment

**Request Body — POST /api/tasks/{taskId}/comments**


| Field   | Type   | Required | Description                     |
| --------- | -------- | ---------- | --------------------------------- |
| content | String | Yes      | Comment text, 1-1000 characters |

```http
POST /api/tasks/42/comments

{
  "content": "I've started working on this. Will update by EOD."
}
```

**Response — 201 Created**

```json
{
  "id": 7,
  "taskId": 42,
  "authorId": 1,
  "authorUsername": "thiennth",
  "content": "I've started working on this. Will update by EOD.",
  "edited": false,
  "createdAt": "2026-03-08T10:30:00Z"
}
```

---

## Tag Module

Tags provide a flexible labelling system for tasks. Tags are global and can be reused across multiple tasks. Any authenticated user can create tags.

### Endpoints


| Method | Endpoint               | Auth         | Description                      |
| -------- | ------------------------ | -------------- | ---------------------------------- |
| GET    | `/api/tags`            | Bearer Token | List all available tags          |
| POST   | `/api/tags`            | Bearer Token | Create a new tag                 |
| GET    | `/api/tags/{id}`       | Bearer Token | Get a tag by ID                  |
| PUT    | `/api/tags/{id}`       | Bearer Token | Update a tag (creator or admin)  |
| DELETE | `/api/tags/{id}`       | Bearer Token | Delete a tag (admin only)        |
| GET    | `/api/tags/{id}/tasks` | Bearer Token | List tasks associated with a tag |

### Tag Model


| Field     | Type    | Description                             |
| ----------- | --------- | ----------------------------------------- |
| id        | Long    | Auto-generated primary key              |
| name      | String  | Tag name, unique, max 50 characters     |
| color     | String  | Hex color code e.g.`#2E75B6` (optional) |
| createdBy | Long    | User ID of creator                      |
| createdAt | Instant | Creation timestamp                      |

### Create Tag

**Request Body — POST /api/tags**


| Field | Type   | Required | Description                      |
| ------- | -------- | ---------- | ---------------------------------- |
| name  | String | Yes      | Unique tag name, 1-50 characters |
| color | String | No       | Hex color code, e.g.`#FF5733`    |

```http
POST /api/tags

{
  "name": "backend",
  "color": "#2E75B6"
}
```

---

## Error Handling

All errors return a consistent JSON structure. The API uses a global exception handler implemented with `@RestControllerAdvice`.

### Error Response Format

```json
{
  "status": 404,
  "message": "Task not found with id: 99",
  "timestamp": "2026-03-08T10:30:00Z"
}
```

### HTTP Status Code Reference


| Status                    | Meaning            | When It Occurs                             |
| --------------------------- | -------------------- | -------------------------------------------- |
| 200 OK                    | Success            | GET, PUT, PATCH succeeded                  |
| 201 Created               | Resource created   | POST created a new resource                |
| 204 No Content            | Deleted            | DELETE succeeded                           |
| 400 Bad Request           | Validation failed  | `@Valid` annotation failed on request body |
| 401 Unauthorized          | Not authenticated  | Missing or invalid JWT token               |
| 403 Forbidden             | Not authorised     | Authenticated but insufficient permissions |
| 404 Not Found             | Resource missing   | Task, user, tag, or comment not found      |
| 409 Conflict              | Duplicate resource | Email or username already exists           |
| 500 Internal Server Error | Server error       | Unexpected database or server failure      |

### Exception Layer Mapping

```
Repository layer  →  DataAccessException        →  500
Service layer     →  Custom exceptions           →  4xx
Controller layer  →  MethodArgumentNotValidException  →  400
```

---

## Database Schema

```sql
CREATE TABLE users (
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    first_name  VARCHAR(100),
    last_name   VARCHAR(100),
    role        VARCHAR(20)  NOT NULL DEFAULT 'USER',
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE TABLE tasks (
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(200) NOT NULL,
    description  TEXT,
    status       VARCHAR(20)  NOT NULL DEFAULT 'TODO',
    priority     VARCHAR(20)  NOT NULL DEFAULT 'MEDIUM',
    due_date     DATE,
    owner_id     BIGINT NOT NULL REFERENCES users(id),
    assignee_id  BIGINT REFERENCES users(id),
    created_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE comments (
    id          BIGSERIAL PRIMARY KEY,
    task_id     BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    author_id   BIGINT NOT NULL REFERENCES users(id),
    content     VARCHAR(1000) NOT NULL,
    edited      BOOLEAN NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ
);

CREATE TABLE tags (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    color       VARCHAR(7),
    created_by  BIGINT NOT NULL REFERENCES users(id),
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE task_tags (
    task_id  BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    tag_id   BIGINT NOT NULL REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (task_id, tag_id)
);
```

---

### Request Flow

```
HTTP Request -> JwtFilter -> Controller -> Service (@Transactional) -> Repository (JdbcTemplate) -> PostgreSQL
```
