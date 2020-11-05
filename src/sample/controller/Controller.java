package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.ConnectUtil;
import sample.model.ModelTableCourses;
import sample.model.ModelTableLessons;
import sample.model.ModelTableTeacher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Controller {
    Connection conn;
    public Controller() throws ClassNotFoundException {conn= ConnectUtil.conDB();}
    @FXML
    private TableView<ModelTableCourses> txtTableCourses;
    @FXML
    private TableColumn<ModelTableCourses,String> txtCoursId;
    @FXML
    private TableColumn<ModelTableCourses,String> txtCoursTitle;
    @FXML
    private TableColumn<ModelTableCourses,String> txtCoursLength;
    @FXML
    private TableColumn<ModelTableCourses,String> txtCoursDescription;



    @FXML
    private TableView<ModelTableLessons> txtTableLessons;
    @FXML
    private TableColumn<ModelTableLessons,String> txtLessonsId;
    @FXML
    private TableColumn<ModelTableLessons,String> txtLessonsTeacher;
    @FXML
    private TableColumn<ModelTableLessons,String> txtLessonsRoom;
    @FXML
    private TableColumn<ModelTableLessons,String> txtLessonsCourse;
    @FXML
    private TableColumn<ModelTableLessons,String> txtLessonsDate;



    @FXML
    private TableView<ModelTableTeacher> txtTableTeachers;
    @FXML
    private TableColumn<ModelTableTeacher,String>txtTeachersId;
    @FXML
    private TableColumn<ModelTableTeacher,String>txtTeachersName;
    @FXML
    private TableColumn<ModelTableTeacher,String>txtTeacherAddr;
    @FXML
    private TableColumn<ModelTableTeacher,String>txtTeacherPhone;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtLength;
    @FXML
    private Button buttonUpdate;
    @FXML
    private TextField txtId;
    @FXML
    private Label status;
    @FXML
    private TextField txtLid;
    @FXML
    private TextField txtLTeacher;
    @FXML
    private TextField txtLCourse;
    @FXML
    private TextField txtLRoom;
    @FXML
    private TextField txtLlesson_date;
    @FXML
    private Label statusLessons;
    private static final DateTimeFormatter dtr=DateTimeFormatter.ofPattern("yyyyy/MM/dd HH:mm:ss");
    ObservableList<ModelTableCourses> listCourses = FXCollections.observableArrayList();
    ObservableList<ModelTableLessons>listLessons = FXCollections.observableArrayList();
    ObservableList<ModelTableTeacher>listTeacher = FXCollections.observableArrayList();


    public void initialize(){
        try {
            String sql = "SELECT * FROM courses";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                listCourses.add(new ModelTableCourses(result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4)));
            }
            txtCoursId.setCellValueFactory(new PropertyValueFactory<>("id"));
            txtCoursTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            txtCoursLength.setCellValueFactory(new PropertyValueFactory<>("length"));
            txtCoursDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            txtTableCourses.setItems(listCourses);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            String sql="SELECT * From lessons";
            PreparedStatement statement= conn.prepareStatement(sql);
            ResultSet result= statement.executeQuery();
            while (result.next()){
                listLessons.add(new ModelTableLessons(result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5)));
            }
            txtLessonsId.setCellValueFactory(new PropertyValueFactory<>("id"));
            txtLessonsCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
            txtLessonsDate.setCellValueFactory(new PropertyValueFactory<>("lesson_date"));
            txtLessonsRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
            txtLessonsTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
            txtTableLessons.setItems(listLessons);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            String sql="SELECT * FROM teachers";
            PreparedStatement statement=conn.prepareStatement(sql);
            ResultSet result=statement.executeQuery();
            while (result.next()){
                listTeacher.add(new ModelTableTeacher(result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4)));
            }
            txtTeachersId.setCellValueFactory(new PropertyValueFactory<>("id"));
            txtTeachersName.setCellValueFactory(new PropertyValueFactory<>("name"));
            txtTeacherAddr.setCellValueFactory(new PropertyValueFactory<>("address"));
            txtTeacherPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            txtTableTeachers.setItems(listTeacher);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @FXML
    public void pressDelete(ActionEvent event) throws SQLException {
        String sql = "DELETE FROM courses WHERE id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,txtId.getText());
        int res = statement.executeUpdate();
        if (res == 1){
            status.setText("Удаление записи выполнено");
        } else status.setText("Ошибка удаления");

    }
    @FXML
    public void pressUpdate(ActionEvent event) throws IOException {
        buttonUpdate.getScene().getWindow().hide();
        Stage primaryStage= new Stage();
        Parent root= null;
        root = FXMLLoader.load(getClass().getResource("/sample/window/sample.fxml"));
        primaryStage.setTitle("База данных");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
    @FXML
    public void pressAdd(ActionEvent event) throws SQLException {
        String sql = "INSERT INTO courses(title, length, description) VALUE (?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, txtTitle.getText());
        statement.setString(2, txtLength.getText());
        statement.setString(3, txtDescription.getText());
        int res = statement.executeUpdate();
        if (res == 1){
            status.setText("Добавление выполнено");
        } else status.setText("Ошибка добавления");
    }
    @FXML
    public void pressAddLesson(ActionEvent event) throws SQLException {
        LocalDateTime lad=LocalDateTime.now();
        String sql = "INSERT INTO lessons(teacher, course, room, lesson_date) VALUE (?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, txtLTeacher.getText());
        statement.setString(2, txtLCourse.getText());
        statement.setString(3, txtLRoom.getText());
        statement.setString(4,dtr.format(lad));
        int res = statement.executeUpdate();
        if (res == 1){
            statusLessons.setText("Добавление выполнено");
        } else statusLessons.setText("Ошибка добавления");
    }
    @FXML
    public void pressDeleteLesson(ActionEvent event) throws SQLException {
        String sql = "DELETE FROM lessons WHERE id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,txtLid.getText());
        int res = statement.executeUpdate();
        if (res == 1){
            statusLessons.setText("Удаление записи выполнено");
        } else statusLessons.setText("Ошибка удаления");


    }


}


