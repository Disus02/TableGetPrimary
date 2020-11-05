package sample.model;

public class ModelTableCourses {
    private String id;
    private String title;
    private String length;
    private String description;
    public ModelTableCourses(String id,String title,String length,String description){
        this.id=id;
        this.title=title;
        this.length=length;
        this.description=description;
    }
    public String getId(){
        return id;
    }

    public String getLength() {
        return length;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
