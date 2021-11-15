package kernel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kernel.dao.User;
import kernel.views.LoginViewController;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage; // 当前场景
    private User nowUser;       // 当前系统用户
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("报刊订阅管理系统");
        ShowLoginView();
    }
    public void ShowLoginView() {
        try {// 载入登录页面
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/LoginWindows.fxml"));
            Parent root = loader.load();
            LoginViewController loginViewController = loader.getController();
            primaryStage.setScene(new Scene(root, 1253, 600));
            primaryStage.show();
            System.out.println(loginViewController);
            loginViewController.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    public User getNowUser() { return nowUser; }

    public void setNowUser(User nowUser) { this.nowUser = nowUser; }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
