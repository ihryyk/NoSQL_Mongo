package org.task_manager.model.dao;

import dev.morphia.query.MorphiaCursor;
import dev.morphia.query.filters.Filters;
import lombok.Data;
import org.bson.types.ObjectId;
import org.task_manager.model.entity.Subtask;
import org.task_manager.model.entity.Task;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TaskManagerDao {


    public List<Task> findAllTasks() {
        return DataSource.getDataStore().find(Task.class).stream().toList();
    }

    public List<Task> findOverdueTasks() {
        Date now = new Date();
        try (MorphiaCursor<Task> cursor = DataSource.getDataStore().find(Task.class)
                .filter(Filters.lt("deadline", now)).iterator()) {
            return cursor.toList();
        }
    }

    public List<Task> findTasksByCategory(String category) {
        try (MorphiaCursor<Task> cursor = DataSource.getDataStore().find(Task.class)
                .filter(Filters.eq("category", category)).iterator()) {
            return cursor.toList();
        }
    }

    public List<Subtask> findSubtasksByTaskCategory(String category) {
        try (MorphiaCursor<Task> cursor = DataSource.getDataStore().find(Task.class)
                .filter(Filters.eq("category", category))
                .iterator()) {
            List<Task> tasks = cursor.toList();
            return tasks.stream()
                    .flatMap(task -> task.getSubTasks().stream())
                    .collect(Collectors.toList());
        }
    }

    public Task saveTask(Task task) {
        return DataSource.getDataStore().save(task);
    }

    public void deleteTaskById(String taskId) {
        DataSource.getDataStore().find(Task.class)
                .filter(Filters.eq("_id", new ObjectId(taskId)))
                .delete();
    }

    public Task findTaskById(String taskId) {
        Task task = DataSource.getDataStore().find(Task.class)
                .filter(Filters.eq("_id", new ObjectId(taskId)))
                .first();
        isTaskExist(task, taskId);
        return task;
    }

    private void isTaskExist(Task task, String taskId) {
        if (task == null) {
            throw new IllegalArgumentException("Task with id " + taskId + " does not exist");
        }
    }

    public List<Subtask> searchSubTasksByName(String searchString) {
        try (MorphiaCursor<Task> cursor = DataSource.getDataStore().find(Task.class)
                .filter(Filters.text(searchString))
                .iterator()) {
            List<Task> tasks = cursor.toList();
            return tasks.stream()
                    .flatMap(task -> task.getSubTasks().stream())
                    .filter(subtask -> subtask.getName().contains(searchString))
                    .collect(Collectors.toList());
        }
    }

    public List<Task> searchTasksByDescription(String searchString) {
        try (MorphiaCursor<Task> cursor = DataSource.getDataStore().find(Task.class)
                .filter(Filters.text(searchString))
                .iterator()) {
            return cursor.toList();
        }
    }

}
