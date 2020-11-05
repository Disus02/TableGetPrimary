package sample.model;

public class ModelTableLessons {
    private String id;
    private String teacher;
    private String course;
    private String room;
    private String lesson_date;
    public ModelTableLessons(String id, String teacher, String course,String room,String lesson_date){
        this.id=id;
        this.teacher=teacher;
        this.course=course;
        this.room=room;
        this.lesson_date=lesson_date;
    }

    public String getId() {
        return id;
    }

    public String getCourse() {
        return course;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getRoom() {
        return room;
    }

    public String getLesson_date() {
        return lesson_date;
    }
}
