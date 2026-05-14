# Task Tracker 开发主题记录（Topic）

## 1. 当前项目目标

基于命令行（CLI）实现一个任务追踪器，支持任务的新增、更新、删除、状态流转与按状态查询，并将数据持久化到项目根目录 `tasks.json`。

---

## 2. 当前开发现状（截至目前）

### 已实现

- `add <description>`：新增任务
- `list`：列出全部任务
- `list done|todo|in-progress`：按状态过滤任务
- `update <id> <description>`：更新任务描述
- `delete <id>`：删除指定任务
- `mark-in-progress <id>`：标记任务为进行中
- `mark-done <id>`：标记任务为已完成
- 数据持久化：读取/写入 `tasks.json`
- 任务字段：`id`、`description`、`status`、`createdAt`、`updatedAt`

### 未实现

- 暂无（核心需求功能已覆盖，仍可继续完善参数与边界处理）

### 已知差距（相对需求）

- 使用了 Jackson 依赖（与“无外部库”约束不一致）

---

## 3. 建议开发顺序（下一阶段）

1. 继续补齐参数与异常处理覆盖（新增场景按需补测）
2. 视需求补充 `TaskService` 与端到端 CLI 用例
3. 若需严格贴合题目约束，可评估移除外部 JSON 库

---

## 4. 功能完成检查清单

- [x] 新增任务（add）
- [x] 更新任务（update）
- [x] 删除任务（delete）
- [x] 标记进行中（mark-in-progress）
- [x] 标记已完成（mark-done）
- [x] 列出所有任务（list）
- [x] 列出 `done` 任务
- [x] 列出 `todo` 任务
- [x] 列出 `in-progress` 任务
- [x] 参数与异常处理覆盖（Main 主要分支）

> 说明：其中 `add` 与 `list` 功能在代码中已实现，此处保留复选框用于持续跟踪回归与完善状态。

---

## 5. 命令设计草案（统一风格）

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

---

## 6. 开发记录模板（可追加）

```md
### YYYY-MM-DD
- 完成：
- 变更文件：
- 问题/阻塞：
- 下一步：
```
