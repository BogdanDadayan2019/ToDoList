import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {

    private static Scanner scanner = new Scanner(System.in);
    static ArrayList<Task> tasks = new ArrayList<>();
    
    static int idTaskEncounter = 1;
    static String exitCommand = "0"; 

    // Methode zur Anzeige des Befehlsmenüs
    public static void CommandsMenu(){
        while (true) {        
            System.out.println("\n\n--- Befehle   \n1: Hinzufügen // 2: Anzeigen // 3: Ändern // 4: Löschen // 0: Beenden");
            String inputCommandNumber = scanner.nextLine();

            if (!inputCommandNumber.trim().isEmpty()) {
                switch (inputCommandNumber) {
                    case "1":
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
                    	displayError("Fehler! Ungültiger Befehl. Bitte versuchen Sie es erneut.\n");
                        break;
                }             
            } else {
            	displayError("Fehler! Die Eingabe darf nicht leer sein. Bitte geben Sie einen gültigen Befehl ein.\n");
            }
        }       
    }

    // Methode zum Schließen des Programms
    public static void CloseApp(){
        System.out.println("--- Das Programm wird geschlossen. Auf Wiedersehen!\n");
        System.exit(0);
    }
    
    // Methode zum Hinzufügen einer Aufgabe
    public static void AddTask(){
        while (true) {
            System.out.println("--- Geben Sie den Text der neuen Aufgabe ein. Zum Beenden geben Sie: " + exitCommand); 
            String taskText = scanner.nextLine();

            if (isExitCommand(taskText)) {
                break;
            }

            if (isValidTaskText(taskText)) {
                Task task = new Task(idTaskEncounter++, taskText);
                tasks.add(task);
                FileManager.saveTasksToFile();
                System.out.println("--- Aufgabe wurde erfolgreich hinzugefügt.\n");
                break;
            } else {
            	displayError("Fehler! Die Eingabe darf nicht leer sein. Bitte versuchen Sie es erneut.\n");
            }
        }
    }
    
    // Methode zum Ändern einer Aufgabe
    public static void ChangeTask(){
        while (true) {
            ShowListOfTasks();
            System.out.println("--- Wählen Sie die Aufgabe anhand der ID aus, um den Text zu ändern. Zum Beenden geben Sie: " + exitCommand);

            String inputIdTask = scanner.nextLine();
            if (isExitCommand(inputIdTask)) {
                return;
            }

            if (isValidId(inputIdTask)) {
                Task selectedTask = findTaskById(inputIdTask);
                if (selectedTask != null) {
                    if (confirmAction("ändern")) {
                        String newTaskText = getNewTaskText();
                        if (isValidTaskText(newTaskText)) {
                            selectedTask.setText(newTaskText);
                            FileManager.saveTasksToFile();
                            System.out.println("--- Aufgabe wurde erfolgreich geändert.\n");
                            return;
                        } else {
                        	displayError("Fehler! Die Eingabe darf nicht leer sein.\n");
                        }
                    }
                } else {
                	displayError("Aufgabe mit dieser ID wurde nicht gefunden.\n");
                }
            } else {
            	displayError("Fehler! Die ID muss nur Zahlen enthalten.\n");
            }
        }
    }

    // Methode zum Löschen einer Aufgabe
    public static void DeleteTask(){
        while (true) {
            ShowListOfTasks();
            System.out.println("--- Wählen Sie die Aufgabe anhand der ID aus, um sie zu löschen. Zum Beenden geben Sie: " + exitCommand);

            String _inputIdTask = scanner.nextLine();
            if (isExitCommand(_inputIdTask)) {
                return;
            }

            if (isValidId(_inputIdTask)) {
                Task selectedTask = findTaskById(_inputIdTask);
                if (selectedTask != null) {
                    if (confirmAction("löschen")) {
                        tasks.remove(selectedTask);
                        FileManager.saveTasksToFile();
                        System.out.println("--- Aufgabe wurde erfolgreich gelöscht.\n");
                        return;
                    }
                } else {
                	displayError("Aufgabe mit dieser ID wurde nicht gefunden.\n");
                }
            } else {
            	displayError("Fehler! Die ID muss nur Zahlen enthalten.\n");
            }
        }   
    }

    // Methode zur Anzeige der Aufgabenliste
    public static void ShowListOfTasks(){
        System.out.println("--- Ihre Aufgabenliste: ");
        for (Task _task : tasks) {
            _task.DisplayTask();
        }
    }

    // Methode zur Überprüfung auf den Exit-Befehl
    public static boolean isExitCommand(String input) {
        return input.equals(exitCommand);
    }

    // Methode zur Überprüfung, ob der Text der Aufgabe gültig ist
    public static boolean isValidTaskText(String text) {
        return !text.trim().isEmpty();
    }

    // Methode zur Überprüfung der ID auf Gültigkeit
    public static boolean isValidId(String id) {
        return id.matches("\\d+");
    }

    // Methode zum Finden einer Aufgabe anhand der ID
    public static Task findTaskById(String id) {
        for (Task task : tasks) {
            if (task.getId() == Integer.parseInt(id)) {
                return task;
            }
        }
        return null;
    }

    // Methode zur Bestätigung der Aktion mit einer Überprüfung der Eingabe
    public static boolean confirmAction(String action) {
        while (true) {
            System.out.println("--- Sind Sie sicher, dass Sie diese Aufgabe " + action + " möchten? Geben Sie '1' zur Bestätigung oder '2' zum Abbrechen ein.");
            String confirmation = scanner.nextLine();

            // Überprüfung auf gültige Eingabe
            if (confirmation.equals("1")) {
                return true; // Bestätigt
            } else if (confirmation.equals("2")) {
                return false; // Abgebrochen
            } else {
            	displayError("Fehler! Bitte geben Sie '1' zur Bestätigung oder '2' zum Abbrechen ein.");
            }
        }
    }

    // Methode zur Eingabe des neuen Texts einer Aufgabe
    public static String getNewTaskText() {
        System.out.println("--- Geben Sie den neuen Text der Aufgabe ein: ");
        return scanner.nextLine();
    }
    
    // Methode zur Display Errors
    public static void displayError(String text) {
    	System.out.println("--- " + text);
    }
    
}
