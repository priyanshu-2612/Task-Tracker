# Task Tracker Backend

A simple REST API built with Spring Boot for managing task lists and their associated tasks.

## Entity Structure

### TaskListDTO

```json
{
  "id": "uuid",
  "title": "Work Tasks",
  "description": "Tasks related to work",
  "count": 5,
  "progress": 0.6,
  "tasks": [
    {
      "id": "uuid",
      "title": "Prepare report",
      "description": "Finish Q2 financial report",
      "dueDate": "2025-07-14T23:59:00",
      "priority": "HIGH",
      "status": "IN_PROGRESS"
    }
  ]
}
```

### TaskDTO

```json
{
  "id": "uuid",
  "title": "Prepare report",
  "description": "Finish Q2 financial report",
  "dueDate": "2025-07-14T23:59:00",
  "priority": "HIGH",
  "status": "IN_PROGRESS"
}
```

## API Endpoints

### Task Lists

| Method | Endpoint                     | Description         |
|--------|------------------------------|---------------------|
| GET    | `/task-lists`                | List task lists     |
| POST   | `/task-lists`                | Create task list    |
| GET    | `/task-lists/{task_list_id}` | Get task list by ID |
| PUT    | `/task-lists/{task_list_id}` | Update task list    |
| DELETE | `/task-lists/{task_list_id}` | Delete task list    |

### Tasks (within a Task List)

| Method | Endpoint                                                      | Description    |
|--------|---------------------------------------------------------------|----------------|
| GET    | `/task-lists/{task_list_id}/tasks`                            | List tasks     |
| POST   | `/task-lists/{task_list_id}/tasks`                            | Create task    |
| GET    | `/task-lists/{task_list_id}/tasks/{task_id}`                  | Get task by ID |
| PUT    | `/task-lists/{task_list_id}/tasks/{task_id}`                  | Update task    |
| DELETE | `/task-lists/{task_list_id}/tasks/{task_id}`                  | Delete task    |

## Example

## Request Payloads

### Create Task List (`POST /task-lists`)

```json
{
  "title": "Work Tasks",
  "description": "Tasks related to work"
}
```

### Create Task (`POST /task-lists/{task_list_id}/tasks`)

```json
{
  "title": "Prepare report",
  "description": "Finish Q2 financial report",
  "dueDate": "2025-07-14T23:59:00",
  "priority": "HIGH"
}
```

> Valid values for `priority`: `"LOW"`, `"MEDIUM"` (default), `"HIGH"`  
> `dueDate` is optional and can be set to `null`

