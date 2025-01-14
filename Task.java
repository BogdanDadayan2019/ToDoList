public class Task{

    private int id;
    private String text;
    private String priority;

    public Task(int id, String text, String priority) {
        this.id = id;
        this.text = text;
        this.priority = priority;
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

    public String getPriority() {
        return priority;
    }

    public void  setPriority(String priority) {
        this.priority = priority;
    }
    
}
