# Copilot instructions for `task-tracker`

## Build and test commands

Use the Maven wrapper at repo root.

- **Build/compile**  
  - Windows: `.\mvnw.cmd -q compile`  
  - macOS/Linux: `./mvnw -q compile`
- **Run full tests**  
  - Windows: `.\mvnw.cmd -q test`  
  - macOS/Linux: `./mvnw -q test`
- **Run a single test (when test classes exist)**  
  - Windows: `.\mvnw.cmd -Dtest=TaskServiceTest test`  
  - Single method: `.\mvnw.cmd -Dtest=TaskServiceTest#addsTask test`

No dedicated lint command/plugin is configured in `pom.xml`.

## High-level architecture

This is a small CLI app with a linear flow:

1. `com.tasktracker.Main` parses command-line args and dispatches supported commands (`add`, `list`).
2. `com.tasktracker.service.TaskService` contains task behaviors:
   - `addTask`: loads tasks, computes next id, sets timestamps/status, persists, prints success message.
   - `listTasks`: loads and prints each task.
3. `com.tasktracker.storage.TaskStorage` is the persistence boundary, fixed to `tasks.json`.
4. `com.tasktracker.util.JsonUtil` performs Jackson read/write for `List<Task>`.
5. `com.tasktracker.model.Task` is the JSON-backed model used across layers.

Runtime data is file-based (no DB): tasks are stored in repo-root `tasks.json`.

## Project-specific conventions

- Commands are handled in `Main` via a `switch` on `args[0]`; user-facing CLI messages are plain `System.out.println(...)`.
- Task IDs are generated as `max(existing ids) + 1` in `TaskService` (no UUID/sequence service).
- Task timestamps are stored as `LocalDateTime.now().toString()` string values in the model/JSON.
- New tasks default to status `"todo"`.
- Persistence currently uses a fixed filename (`tasks.json`) via `TaskStorage.FILE_NAME`.
- `tt.bat` is the Windows launcher: it compiles on first run if needed, builds `target\classpath.txt`, then runs `com.tasktracker.Main`.
- `install.bat` / `install.sh` are intended for PATH setup and initial compile/classpath generation.

