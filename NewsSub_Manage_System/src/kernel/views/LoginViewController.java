package kernel.views;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import kernel.Main;
import kernel.NowUser;
import kernel.dao.Admin;
import kernel.dao.GeneralUser;
import kernel.util.CheckerUtil;
import kernel.util.DbUtils;
import kernel.util.SysHintUtil;

import java.util.ArrayList;
import java.util.List;

public class LoginViewController {
    // 登录界面

    @FXML
    private VBox loginView;                 // 登录视图 切换时使用

    @FXML
    private TextField usernameField;        // 登录用户名

    @FXML
    private PasswordField passwordField;    // 登录密码


    @FXML
    private Button loginButton;             // 登录按钮

    @FXML
    private Button signUpButton;            // 注册按钮

    @FXML
    private CheckBox isAdmin;               // 是否为管理员打勾盒子

    // 注册界面

    @FXML
    private VBox signupView;                 // 注册视图 切换时使用

    @FXML
    private TextField signupUserNameField;        // 注册用户名

    @FXML
    private TextField signupPassWordField;        // 注册用户密码

    @FXML
    private TextField signupRealNameField;        // 注册用户真实姓名

    @FXML
    private TextField signupIdCardField;        // 注册用户身份证号

    @FXML
    private TextField signupPhoneField;        // 注册用户联系电话

    @FXML
    private TextField signupAddressField;        // 注册用户联系地址

    @FXML
    private Button signupReturnButton;            // 返回按钮

    @FXML
    private Button comfirmSignupButton;            // 确认注册按钮

    @FXML
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    public Main getMainApp() {
        return mainApp;
    }

    /**
     * 登录句柄，点击登录按钮后进入
     * @throws Exception 抛出登陆异常
     */
    public void handleLogin() throws Exception {
        // 调试用
        System.out.println("点击了登录按钮");
        // 构建查询表单，准备查询登录信息
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = null;
        // 判断是否为管理员 并选择相应的数据库
        if(isAdmin.isSelected()) {
            sql = "select * from `admin` where userName = ? and passWord = ?";
        } else {
            sql = "select * from `generalUser` where userName = ? and passWord = ?";
        }
        List<Object> params = new ArrayList<>();
        params.add(usernameField.getText());
        params.add(passwordField.getText());
        //调试用
        System.out.println(usernameField.getText());
        System.out.println(passwordField.getText());
        try{
            if(isAdmin.isSelected()) {
                Admin admin = dbUtils.findSimpleProResult(sql, params, Admin.class);
                if (admin == null) {
                    throw new NullPointerException("出错了！请检查用户名或密码是否有误！");
                }
                System.out.println("用户名密码正确！");//调试用
                // 初始化当前用户为管理员
                System.out.println(admin);
                mainApp.setNowUser(admin);
                NowUser.setId(admin.getId());
                NowUser.setType("管理员");
                NowUser.setUsername(admin.getUserName());
            } else {
                GeneralUser generalUser = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
                if (generalUser == null) {
                    throw new NullPointerException("出错了！请检查用户名或密码是否有误！");
                }
                System.out.println("用户名密码正确！");//调试用
                // 初始化当前用户为普通用户
                System.out.println(generalUser);
                mainApp.setNowUser(generalUser);
                NowUser.setId(generalUser.getId());
                NowUser.setType("普通用户");
                NowUser.setUsername(generalUser.getUserName());
            }
            System.out.println("登录成功！");

            // 登录成功，进入相应界面
            FXMLLoader loader = new FXMLLoader();
            if (isAdmin.isSelected()) {
                loader.setLocation((Main.class.getResource("views/AdminWindows.fxml")));
            } else {
                loader.setLocation(Main.class.getResource("views/GeneralUserWindows.fxml"));
            }
            /*
             *==================================================
             *                 !!important!!
             *     你必须先调用loader.load才能找到controller
             *==================================================
             */
            Parent newView = loader.load();
            System.out.println("加载成功！");
            if (isAdmin.isSelected()) {
                AdminViewController adminVIewController = loader.getController();
                System.out.println(adminVIewController);
                System.out.println("mainApp: "+ mainApp);
                adminVIewController.setMainApp(mainApp);
            } else {
                GeneralUserController generalUserController = loader.getController();
                System.out.println("mainApp: " + mainApp);
                generalUserController.setMainApp(mainApp);
            }
            mainApp.getPrimaryStage().setScene(new Scene(newView, 1253, 830));
        } catch (Exception e) {
            System.out.println("加载页面失败！");
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), e.getMessage());
            e.printStackTrace();
            usernameField.setText("");
            passwordField.setText("");
            usernameField.requestFocus();
        }
    }

    /**
     * 注册句柄，点击确认注册按钮后进入
     * @throws Exception 抛出注册异常
     */
    public void handleSignup() throws Exception {
        // 调试用
        System.out.println("点击了确认注册按钮");
        // 检查注册信息
        String errorMessage = CheckerUtil.generalUserSignUpCheck(signupUserNameField.getText(),
                signupPassWordField.getText(),
                signupRealNameField.getText(),
                signupIdCardField.getText(),
                signupPhoneField.getText(),
                signupAddressField.getText()
        );
        if(errorMessage != null) {  // 若检查不通过，直接返回
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
            return;
        } else {
            // 若检查通过，将新用户信息插入数据库
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "insert into `generalUser` (userName, passWord, realName, idCard, phone, address) " +
                    "values(?, ?, ?, ?, ?, ?)";
            List<Object> params = new ArrayList<>();
            params.add(signupUserNameField.getText());
            params.add(signupPassWordField.getText());
            params.add(signupRealNameField.getText());
            params.add(signupIdCardField.getText());
            params.add(signupPhoneField.getText());
            params.add(signupAddressField.getText());
            try {
                // 添加用户
                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "添加用户成功!");
            } catch (Exception e) {
                e.printStackTrace();
                SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "添加用户失败!\n可能出了什么问题");
            }
            // 注册成功，准备进入用户界面;
            // 先认证全局用户
            sql = "select * from `generalUser` where userName = ?";
            params.clear();
            params.add(signupUserNameField.getText());
            GeneralUser  nowUser = new GeneralUser(signupUserNameField.getText(), signupPassWordField.getText());
            mainApp.setNowUser(nowUser);
            try {
                nowUser = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
                NowUser.setId(nowUser.getId());
                NowUser.setUsername(nowUser.getUserName());
                NowUser.setType("普通用户");
            } catch (Exception e) {
                e.printStackTrace();
                SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "认证失败！");
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/GeneralUserWindows.fxml"));
            Parent newView = loader.load();
            System.out.println("加载成功！");
            GeneralUserController generalUserController = loader.getController();
            generalUserController.setMainApp(mainApp);
            mainApp.getPrimaryStage().setScene(new Scene(newView, 1253, 830));
        }
    }
    /**
     * 点击登录界面的注册按钮后跳转登陆界面
     */
    @FXML
    void jumptoSignupView() {
        System.out.println("注册按钮被点击了");
        loginView.setVisible(false);
        signupView.setVisible(true);
    }

    /**
     * 点击注册界面的返回按钮后跳转登陆界面
     */
    @FXML
    void jumptoLoginView() {
        System.out.println("登录按钮被点击了");
        signupView.setVisible(false);
        loginView.setVisible(true);
    }

}
