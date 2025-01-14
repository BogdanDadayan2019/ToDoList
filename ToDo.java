package toDo;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDo {

    private static Scanner scanner = new Scanner(System.in);
    static ArrayList<Task> tasks = new ArrayList<>();
    static String filePath = "tasks.txt";
    static int idTaskEncounter = 1;
    static String exitStr = "0"; 

    
    public static void main(String[] args) {     
        System.out.println("--- Добро пожаловать в ToDoList ");
        loadTasksFromFile();  // Загружаем задачи из файла
        CommandsMenu();       // Показываем меню команд
    }

    // Метод для вывода меню команд
    public static void CommandsMenu(){
        while (true) {        
            System.out.println("\n\n--- Команды   \n1: Добавить // 2: Показать // 3: Изменить // 4: Удалить // 0: Выйти");
            String inputCommandNumber = scanner.nextLine();

            if (!inputCommandNumber.trim().isEmpty()) {
                switch (inputCommandNumber) {
                    case "21":
                        AddTask();
                        break;
                    case "2":
                        ShowListOfTasks();
                        break;
                    case "3":
                        ChangeTask();
                        break;
                    case "4":
                        DeleteTask();
                        break;
                    case "0":
                        CloseApp();
                        break;              
                    default:
                    	displayError("Ошибка! Некорректная команда. Попробуйте еще раз.\n");
                        break;
                }             
            } else {
            	displayError("Ошибка! Ввод не может быть пустым. Пожалуйста, введите корректную команду.\n");
            }
        }       
    }

    // Метод для закрытия программы
    public static void CloseApp(){
        System.out.println("--- Программа закрывается. До свидания!\n");
        System.exit(0);
    }
    
    // Метод для добавления задачи
    public static void AddTask(){
        while (true) {
            System.out.println("--- Введите текст новой задачи. Для выхода введите: " + exitStr); 
            String taskText = scanner.nextLine();

            if (isExitCommand(taskText)) {
                break;
            }

            if (isValidTaskText(taskText)) {
                Task task = new Task(idTaskEncounter++, taskText);
                tasks.add(task);
                saveTasksToFile();
                System.out.println("--- Задача добавлена успешно.\n");
                break;
            } else {
            	displayError("Ошибка! Ввод не может быть пустым. Попробуйте снова.\n");
            }
        }
    }

    // Метод для изменения задачи
    public static void ChangeTask(){
        while (true) {
            ShowListOfTasks();
            System.out.println("--- Выберите задачу по ID для изменения её текста. Для выхода введите: " + exitStr);

            String inputIdTask = scanner.nextLine();
            if (isExitCommand(inputIdTask)) {
                return;
            }

            if (isValidId(inputIdTask)) {
                Task selectedTask = findTaskById(inputIdTask);
                if (selectedTask != null) {
                    if (confirmAction("изменить")) {
                        String newTaskText = getNewTaskText();
                        if (isValidTaskText(newTaskText)) {
                            selectedTask.setText(newTaskText);
                            saveTasksToFile();
                            System.out.println("--- Задача успешно изменена.\n");
                            return;
                        } else {
                        	displayError("Ошибка! Ввод не может быть пустым.\n");
                        }
                    }
                } else {
                	displayError("Задача с таким ID не найдена.\n");
                }
            } else {
            	displayError("Ошибка! ID должен содержать только цифры.\n");
            }
        }
    }

    // Метод для удаления задачи
    public static void DeleteTask(){
        while (true) {
            ShowListOfTasks();
            System.out.println("--- Выберите задачу по ID для удаления. Для выхода введите: " + exitStr);

            String _inputIdTask = scanner.nextLine();
            if (isExitCommand(_inputIdTask)) {
                return;
            }

            if (isValidId(_inputIdTask)) {
                Task selectedTask = findTaskById(_inputIdTask);
                if (selectedTask != null) {
                    if (confirmAction("удалить")) {
                        tasks.remove(selectedTask);
                        saveTasksToFile();
                        System.out.println("--- Задача успешно удалена.\n");
                        return;
                    }
                } else {
                	displayError("Задача с таким ID не найдена.\n");
                }
            } else {
            	displayError("Ошибка! ID должен содержать только цифры.\n");
            }
        }   
    }

    // Метод для отображения списка задач
    public static void ShowListOfTasks(){
        System.out.println("--- Список ваших задач: ");
        for (Task _task : tasks) {
            _task.DisplayTask();
        }
    }

    // Метод для загрузки задач из файла
    public static void loadTasksFromFile() {
        System.out.println("--- Попытка загрузить задачи из файла...");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskParts = line.split(":", 2);
                int id = Integer.parseInt(taskParts[0]);
                String text = taskParts[1];
                tasks.add(new Task(id, text));
                idTaskEncounter = Math.max(idTaskEncounter, id + 1); 
            }
        } catch (IOException e) {
        	displayError("Ошибка при загрузке задач из файла: " + e.getMessage());
        }
    }

    // Метод для сохранения задач в файл
    public static void saveTasksToFile() {
        System.out.println("--- Сохранение задач в файл...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.getId() + ":" + task.getText());
                writer.newLine();
            }
        } catch (IOException e) {
        	displayError("Ошибка при сохранении задач в файл: " + e.getMessage());
        }
    }

    // Метод для проверки на выходную команду
    public static boolean isExitCommand(String input) {
        return input.equals(exitStr);
    }

    // Метод для проверки текста задачи на пустоту
    public static boolean isValidTaskText(String text) {
        return !text.trim().isEmpty();
    }

    // Метод для проверки ID на корректность
    public static boolean isValidId(String id) {
        return id.matches("\\d+");
    }

    // Метод для поиска задачи по ID
    public static Task findTaskById(String id) {
        for (Task task : tasks) {
            if (task.getId() == Integer.parseInt(id)) {
                return task;
            }
        }
        return null;
    }

    // Метод для подтверждения действия с проверкой на корректный ввод
    public static boolean confirmAction(String action) {
        while (true) {
            System.out.println("--- Вы уверены, что хотите " + action + " эту задачу? Для подтверждения введите '1', для отмены введите '2'.");
            String confirmation = scanner.nextLine();

            // Проверка на корректный ввод
            if (confirmation.equals("1")) {
                return true; // Подтверждено
            } else if (confirmation.equals("2")) {
                return false; // Отменено
            } else {
            	displayError("Ошибка! Пожалуйста, введите '1' для подтверждения или '2' для отмены.");
            }
        }
    }

    // Метод для получения нового текста задачи
    public static String getNewTaskText() {
        System.out.println("--- Введите новый текст задачи: ");
        return scanner.nextLine();
    }
    
    public static void displayError(String text) {
    	System.out.println("--- " + text);
    }
}
