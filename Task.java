package toDo;


public class Task{

    private int id;
    private String text;


    public Task(int id, String text) {
        this.id = id;
        this.text = text;
    }
    
    public void DisplayTask(){
        System.out.println("ID: " + this.id + ", Task: " + this.text);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
    
}
