package sample.controller;

import com.sun.javafx.css.StyleCache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.ConnectUtil;

import java.io.IOException;
import java.sql.*;

public class ControllerReg {
    Connection conn;
    public ControllerReg() throws ClassNotFoundException {conn= ConnectUtil.conDB();
    }
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label labelLogin;
    @FXML
    private Label labelEmail;
    @FXML
    private Label status;
    @FXML
    private Button buttonToWin1;
    String login;
    String email;
    String chekLogin;
    String chekEmail;
    @FXML
    public void pressReg(ActionEvent event) throws SQLException {
        labelEmail.setText("");
        labelLogin.setText("");
        status.setText("");
        String sql="SELECT * FROM users WHERE login=? or email=?";
        PreparedStatement statement=conn.prepareStatement(sql);
        login=txtLogin.getText();
        email=txtEmail.getText();
        statement.setString(1,login);
        statement.setString(2,email);
        ResultSet resultSet=statement.executeQuery();
        while (resultSet.next()){
            chekLogin=resultSet.getString("login");
            chekEmail=resultSet.getString("email");
           if (login.equals(chekLogin)){
               labelLogin.setText("Логин уже существует");
           }
           if (email.equals(chekEmail)){
               labelEmail.setText("Email уже существует");
           }
        }

        if (!login.equals(chekLogin)&&!email.equals(chekEmail)) {
            labelLogin.setText("");
            labelEmail.setText("");
            String sqlReg = "INSERT INTO users(firstName,lastName,email,login,password) VALUE(?,?,?,?,?)";
            PreparedStatement statementReg = conn.prepareStatement(sqlReg, Statement.RETURN_GENERATED_KEYS);
            statementReg.setString(1, txtFirstName.getText());
            statementReg.setString(2, txtLastName.getText());
            statementReg.setString(3, txtEmail.getText());
            statementReg.setString(4, txtLogin.getText());
            statementReg.setString(5, txtPassword.getText());
            int result = statementReg.executeUpdate();
            if (result == 1) {
                ResultSet resultReg = statementReg.getGeneratedKeys();
                while (resultReg.next()) {
                    int id = resultReg.getInt(1);
                    status.setText(txtFirstName.getText() + " " + txtLastName.getText() + " зарегистрирован id= " + id);
                }

            }
        }
    }
    @FXML
    public void pressOpenSignIn(ActionEvent event) throws IOException {
        buttonToWin1.getScene().getWindow().hide();
        Stage primaryStage= new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("/sample/window/SignIn.fxml"));
        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


}
