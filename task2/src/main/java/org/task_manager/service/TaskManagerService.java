package org.task_manager.service;

import lombok.Data;
import org.task_manager.model.dao.TaskManagerDao;
import org.task_manager.model.entity.Subtask;
import org.task_manager.model.entity.Task;

import java.util.List;

@Data
public class TaskManagerService {

    private final TaskManagerDao taskManagerDao;

    public Task saveTask(Task task) {
        return taskManagerDao.saveTask(task);
    }

    public void deleteTask(String taskId) {
        taskManagerDao.findTaskById(taskId);
        taskManagerDao.deleteTaskById(taskId);
    }

    public Task deleteAllSubtasks(String taskId) {
        Task task = taskManagerDao.findTaskById(taskId);
        task.getSubTasks().clear();
        return taskManagerDao.saveTask(task);
    }

    public List<Task> findAllTasks() {
        return taskManagerDao.findAllTasks();
    }

    public List<Task> findOverdueTasks() {
        return taskManagerDao.findOverdueTasks();
    }

    public List<Task> findTasksByCategory(String category) {
        return taskManagerDao.findTasksByCategory(category);
    }

    public List<Subtask> findSubtasksByTaskCategory(String category) {
        return taskManagerDao.findSubtasksByTaskCategory(category);
    }

    public List<Task> searchTasksByDescription(String searchString) {
        return taskManagerDao.searchTasksByDescription(searchString);
    }

    public List<Subtask> searchSubtasksByName(String searchString) {
        return taskManagerDao.searchSubTasksByName(searchString);
    }

}
