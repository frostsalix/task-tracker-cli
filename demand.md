# 任务追踪器需求文档

任务追踪器是一个用于跟踪和管理任务的 CLI 项目。目标是通过该项目练习：

- 文件系统操作
- 用户输入处理
- 命令行应用构建

---

## 功能需求

应用应从命令行运行，使用位置参数接收用户输入，并将任务存储在 JSON 文件中。用户应能够：

- 添加任务
- 更新任务
- 删除任务
- 将任务标记为进行中（`in-progress`）
- 将任务标记为已完成（`done`）
- 列出所有任务
- 列出所有已完成任务
- 列出所有未完成任务（`todo`）
- 列出所有进行中的任务（`in-progress`）

---

## 约束条件

- 可使用任意编程语言实现
- 必须使用命令行位置参数接收输入
- 必须在当前目录使用 JSON 文件存储任务
- 若 JSON 文件不存在，应自动创建
- 使用语言自带文件系统能力处理 JSON 文件
- 不使用外部库或框架
- 需要处理错误与边界情况

---

## 命令示例

```bash
# 添加任务
task-cli add "Buy groceries"
# Output: Task added successfully (ID: 1)

# 更新与删除任务
task-cli update 1 "Buy groceries and cook dinner"
task-cli delete 1

# 标记任务状态
task-cli mark-in-progress 1
task-cli mark-done 1

# 列出任务
task-cli list
task-cli list done
task-cli list todo
task-cli list in-progress
```

---

## 任务数据模型

每个任务应包含以下属性：

- `id`：唯一标识符
- `description`：任务描述
- `status`：`todo` / `in-progress` / `done`
- `createdAt`：创建时间
- `updatedAt`：最后更新时间

添加任务时必须写入全部属性；更新任务时必须同步更新相关字段（至少 `updatedAt`）。

---

## 开发步骤建议

1. 搭建开发环境（语言、编辑器、运行环境）
2. 初始化项目与版本控制
3. 先完成 CLI 参数解析骨架
4. 按功能逐步实现并逐步测试（建议顺序：add → list → update/mark/delete）
5. 检查 JSON 数据正确性并处理异常
6. 完成 README 使用说明
