package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.ConnectUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerSignIn {
    Connection conn;
    public ControllerSignIn() throws ClassNotFoundException {conn= ConnectUtil.conDB();
    }
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button buttonToWin2;
    @FXML
    private Label status;
    @FXML
    private Button buttonToWin3;
    String login;
    String password;
    String chekLog;
    String chekPass;
    @FXML
    public void pressOpen(ActionEvent event) throws SQLException, IOException {
        String sql="SELECT * FROM users WHERE login=? and password=?";
        PreparedStatement statement= conn.prepareStatement(sql);
        login=txtLogin.getText();
        password=txtPassword.getText();
        statement.setString(1,login);
        statement.setString(2,password);
        ResultSet result=statement.executeQuery();
        while (result.next()){
            chekLog=result.getString("login");
            chekPass=result.getString("password");
            if (login.equals(chekLog)&&password.equals(chekPass)){
                status.setText("Вы вошли");
                buttonToWin3.getScene().getWindow().hide();
                Stage primaryStage= new Stage();
                Parent root=FXMLLoader.load(getClass().getResource("/sample/window/sample.fxml"));
                primaryStage.setTitle("База Данных");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            }
        }
        if (!login.equals(chekLog)||!password.equals(chekPass)){
            status.setText("Неправельный логин или пароль");
        }
        if (login.isEmpty()){
            status.setText("Введите логин");
        }
        if (password.isEmpty()){
            status.setText("Введите пароль");
        }

    }
    @FXML
    public void pressReg(ActionEvent event) throws IOException {
        buttonToWin2.getScene().getWindow().hide();
       newWindow();

    }
    @FXML
    public void handle(KeyEvent event) throws IOException {
        KeyCode keyCode=event.getCode();
        if (keyCode==KeyCode.ENTER) {
            buttonToWin2.getScene().getWindow().hide();
           newWindow();
        }

    }

    public void newWindow() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/sample/window/Registration.fxml"));
        primaryStage.setTitle("Регистрация");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


}
