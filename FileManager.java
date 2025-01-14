package toDo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	
	static String filePath = "tasks.txt";
	// Methode zum Laden der Aufgaben aus der Datei
    public static void loadTasksFromFile() {
        System.out.println("--- Versuche, Aufgaben aus der Datei zu laden...");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskParts = line.split(":", 2);
                int id = Integer.parseInt(taskParts[0]);
                String text = taskParts[1];
                TaskManager.tasks.add(new Task(id, text));
                TaskManager.idTaskEncounter = Math.max(TaskManager.idTaskEncounter, id + 1); 
            }
        } catch (IOException e) {
        	TaskManager.displayError("Fehler beim Laden der Aufgaben aus der Datei: " + e.getMessage());
        }
    }

    // Methode zum Speichern der Aufgaben in die Datei
    public static void saveTasksToFile() {
        System.out.println("--- Speichern der Aufgaben in die Datei...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : TaskManager.tasks) {
                writer.write(task.getId() + ":" + task.getText());
                writer.newLine();
            }
        } catch (IOException e) {
        	TaskManager.displayError("Fehler beim Speichern der Aufgaben in die Datei: " + e.getMessage());
        }
    }

}
