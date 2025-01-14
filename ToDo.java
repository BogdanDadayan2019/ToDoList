public class ToDo {

    public static void main(String[] args) {     
        System.out.println("--- Willkommen bei ToDoList ");
        FileManager.loadTasksFromFile();  // Laden der Aufgaben aus der Datei
        TaskManager.CommandsMenu();       // Anzeige des Befehlsmenüs
    }
    
}
