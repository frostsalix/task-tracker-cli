# task-tracker-cli

A Java-based CLI task tracker. Data is stored in `tasks.json` at the repository root.

Also the sample solution for the task-tracker challenge from roadmap.sh.

## Requirements

- JDK (as required by `pom.xml`)
- Windows / macOS / Linux

## Build and Test

Use Maven Wrapper from the repository root:

- Compile (Windows): `.\mvnw.cmd -q compile`
- Compile (macOS/Linux): `./mvnw -q compile`
- Run all tests (Windows): `.\mvnw.cmd -q test`
- Run all tests (macOS/Linux): `./mvnw -q test`
- Run one test class (Windows): `.\mvnw.cmd -Dtest=TaskServiceTest test`
- Run one test method (Windows): `.\mvnw.cmd -Dtest=TaskServiceTest#updatesTaskDescriptionAndUpdatedAt test`

## Run

### Windows

Run directly:

```powershell
.\tt.bat add "Buy groceries"
.\tt.bat list
```

If you want to use `tt` globally, add the repository directory to your user PATH.

### macOS / Linux

Run with Maven:

```bash
./mvnw -q compile
./mvnw -q exec:java -Dexec.mainClass="com.tasktracker.Main" -Dexec.args='add "Buy groceries"'
```

## Supported Commands

```bash
tt add "Buy groceries"
tt update 1 "Buy groceries and cook dinner"
tt delete 1
tt mark-in-progress 1
tt mark-done 1
tt list
tt list done
tt list todo
tt list in-progress
```

## Validation and Edge Cases

- Missing command: `No command provided.`
- Unknown command: `Unknown command.`
- Non-numeric or non-positive `id`: `Invalid task id.`
- Blank description for `add` / `update`: `Description required.`
- `list` supports only: `todo`, `in-progress`, `done`
- Extra arguments are rejected (for example: `Too many arguments for ...`)

## Data Model

Each task includes:

- `id`
- `description`
- `status` (`todo` / `in-progress` / `done`)
- `createdAt`
- `updatedAt`
