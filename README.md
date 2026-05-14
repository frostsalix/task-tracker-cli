# task-tracker-cli

一个基于 Java 的命令行任务追踪工具，数据保存在项目根目录 `tasks.json`。

## 环境要求

- JDK（按 `pom.xml` 配置）
- Windows / macOS / Linux

## 构建与测试

使用 Maven Wrapper（仓库根目录）：

- 编译（Windows）：`.\mvnw.cmd -q compile`
- 编译（macOS/Linux）：`./mvnw -q compile`
- 全量测试（Windows）：`.\mvnw.cmd -q test`
- 全量测试（macOS/Linux）：`./mvnw -q test`
- 单测类（Windows）：`.\mvnw.cmd -Dtest=TaskServiceTest test`
- 单测方法（Windows）：`.\mvnw.cmd -Dtest=TaskServiceTest#updatesTaskDescriptionAndUpdatedAt test`

## 运行方式

### Windows（推荐）

1. 首次初始化（编译 + PATH 配置）：

```powershell
.\install.bat
```

2. 若当前终端里 `tt` 仍不可用，刷新 PATH 或重开终端：

```powershell
$env:Path = [Environment]::GetEnvironmentVariable('Path','Machine') + ';' + [Environment]::GetEnvironmentVariable('Path','User')
```

3. 直接运行：

```powershell
tt add "Buy groceries"
```

> 在 PowerShell 中也可以使用 `.\tt.bat ...` 直接运行当前目录脚本。

### macOS / Linux

```bash
./install.sh
```

随后可使用 `tt ...` 执行命令（或按脚本提示 `source` 对应 shell 配置文件）。

## 支持命令

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

## 参数与边界行为

- 缺少命令：输出 `No command provided.`
- 未知命令：输出 `Unknown command.`
- `id` 非数字或小于等于 0：输出 `Invalid task id.`
- `add` / `update` 描述为空白：输出 `Description required.`
- `list` 仅支持状态：`todo`、`in-progress`、`done`
- 传入多余参数会报错（例如 `Too many arguments for ...`）

## 数据模型

每个任务包含：

- `id`
- `description`
- `status`（`todo` / `in-progress` / `done`）
- `createdAt`
- `updatedAt`

