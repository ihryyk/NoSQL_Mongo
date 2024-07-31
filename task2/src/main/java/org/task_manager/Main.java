package org.task_manager;

import org.task_manager.model.dao.TaskManagerDao;
import org.task_manager.model.entity.Subtask;
import org.task_manager.model.entity.Task;
import org.task_manager.service.TaskManagerService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManagerService taskManagerService = new TaskManagerService(new TaskManagerDao());

        while (true) {
            System.out.println("Please enter a command: ");
            System.out.println("\"allTasks\" - Display all tasks.");
            System.out.println("\"overdueTasks\" - Display overdue tasks.");
            System.out.println("\"insertTask\" - Insert new task.");
            System.out.println("\"deleteTask\" - Delete a task by id.");
            System.out.println("\"deleteAllSubtasks\" - Delete all subtasks of a task by task id.");
            System.out.println("\"findTasksByCategory\" - Display all tasks with a specific category.");
            System.out.println("\"findSubtasksByTaskCategory\" - Display all subtasks related to tasks with a specific category.");
            System.out.println("\"searchTasksByDescription\" - Full-text search tasks by description.");
            System.out.println("\"searchSubtasksByName\" - Full-text search subtasks by name.");

            String commandLine = scanner.nextLine().trim();

            String[] parts = commandLine.split("\\s+", 2);
            String command = parts[0];
            String arg = parts.length > 1 ? parts[1] : "";
            System.out.println(command.equals("allTasks"));
            switch (command) {
                case "allTasks":
                    displayAllTasks(taskManagerService);
                    break;
                case "overdueTasks":
                    displayOverdueTasks(taskManagerService);
                    break;
                case "insertTask":
                    insertTask(taskManagerService);
                    break;
                case "deleteTask":
                    deleteTask(taskManagerService, arg);
                    break;
                case "deleteAllSubtasks":
                    deleteAllSubtasks(taskManagerService, arg);
                    break;
                case "findTasksByCategory":
                    findTasksByCategory(taskManagerService, arg);
                    break;
                case "findSubtasksByTaskCategory":
                    findSubtasksByTaskCategory(taskManagerService, arg);
                    break;
                case "searchTasksByDescription":
                    searchTasksByDescription(taskManagerService, arg);
                    break;
                case "searchSubtasksByName":
                    searchSubtasksByName(taskManagerService, arg);
                    break;
                default:
                    System.out.println("Unknown command: " + command);
            }
        }
    }

    private static void displayAllTasks(TaskManagerService taskManagerService) {
        List<Task> allTasks = taskManagerService.findAllTasks();
        allTasks.forEach(System.out::println);
    }

    private static void displayOverdueTasks(TaskManagerService taskManagerService) {
        List<Task> overdueTasks = taskManagerService.findOverdueTasks();
        overdueTasks.forEach(System.out::println);
    }

    private static void insertTask(TaskManagerService taskManagerService) {
        Task newTask = saveTask();
        Task savedTask = taskManagerService.saveTask(newTask);
        System.out.println("Task saved: " + savedTask);
    }

    public static Task saveTask() {
        System.out.println("Insert Task");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter task name: ");
        String name = scanner.nextLine();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.print("Enter task category: ");
        String category = scanner.nextLine();

        System.out.print("Enter total number of subtasks: ");
        int subtaskCount = scanner.nextInt();
        scanner.nextLine();

        List<Subtask> subTasks = new ArrayList<>();
        for (int i = 0; i < subtaskCount; i++) {
            System.out.print("Enter subtask " + (i + 1) + " name: ");
            String subtaskName = scanner.nextLine();
            System.out.print("Enter subtask " + (i + 1) + " description: ");
            String subtaskDescription = scanner.nextLine();
            subTasks.add(new Subtask(subtaskName, subtaskDescription));
        }

        Date dateOfCreation = new Date();

        System.out.println("Enter days to deadline:");
        int daysToDeadline = scanner.nextInt();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysToDeadline);
        Date deadline = calendar.getTime();

        return new Task(null, dateOfCreation, deadline, name, description, subTasks, category);
    }


    private static void deleteTask(TaskManagerService taskManagerService, String taskId) {
        if (taskId.isEmpty()) {
            System.out.println("You need to specify the task id to delete a task!");
        } else {
            taskManagerService.deleteTask(taskId);
            System.out.println("Task with id '" + taskId + "' has been deleted.");
        }
    }

    private static void deleteAllSubtasks(TaskManagerService taskManagerService, String taskId) {
        if (taskId.isEmpty()) {
            System.out.println("You need to specify the task id to delete all subtasks of a task!");
        } else {
            Task task = taskManagerService.deleteAllSubtasks(taskId);
            if (task == null) {
                System.out.println("There is no task with id '" + taskId + "'.");
            } else {
                System.out.println("All subtasks from task with id '" + taskId + "' has been deleted.");
            }
        }
    }

    private static void findTasksByCategory(TaskManagerService taskManagerService, String category) {
        if (category.isEmpty()) {
            System.out.println("You need to specify the category to find tasks by category!");
        } else {
            List<Task> tasks = taskManagerService.findTasksByCategory(category);
            tasks.forEach(System.out::println);
            if (tasks.isEmpty()) {
                System.out.println("There is no task with category '" + category + "'.");
            }
        }
    }

    private static void findSubtasksByTaskCategory(TaskManagerService taskManagerService, String category) {
        if (category.isEmpty()) {
            System.out.println("You need to specify the category to find subtasks by task category!");
        } else {
            List<Subtask> subtasks = taskManagerService.findSubtasksByTaskCategory(category);
            subtasks.forEach(System.out::println);
            if (subtasks.isEmpty()) {
                System.out.println("There is no subtask with category '" + category + "'.");
            }
        }
    }

    private static void searchTasksByDescription(TaskManagerService taskManagerService, String searchString) {
        if (searchString.isEmpty()) {
            System.out.println("You need to specify the search string to search tasks by description!");
        } else {
            List<Task> tasks = taskManagerService.searchTasksByDescription(searchString);
            tasks.forEach(System.out::println);
            if (tasks.isEmpty()) {
                System.out.println("There is no task with description '" + searchString + "'.");
            }
        }
    }

    private static void searchSubtasksByName(TaskManagerService taskManagerService, String searchString) {
        if (searchString.isEmpty()) {
            System.out.println("You need to specify the search string to search subtasks by name!");
        } else {
            List<Subtask> subtasks = taskManagerService.searchSubtasksByName(searchString);
            subtasks.forEach(System.out::println);
            if (subtasks.isEmpty()) {
                System.out.println("There is no subtask with name '" + searchString + "'.");
            }
        }
    }

}