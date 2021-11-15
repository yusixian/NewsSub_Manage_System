package kernel.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import kernel.Main;

import javafx.scene.image.ImageView;
import kernel.NowUser;
import kernel.dao.GeneralUser;
import kernel.dao.Magazine;
import kernel.dao.Order;
import kernel.util.CheckerUtil;
import kernel.util.DbUtils;
import kernel.util.StatisticsUtil;
import kernel.util.SysHintUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneralUserController {
    // 菜单栏组件
    @FXML
    private Label userNameLabel;    //用户名标签

    // 三个页面面板
    @FXML
    private BorderPane subscriptionPane;            // 订阅报刊面板  最开始进入这个面板

    @FXML
    private BorderPane personalInfoPane;            // 个人信息修改面板

    @FXML
    private BorderPane myOrderPane;                 // 我的订阅面板

    // 订阅报刊界面组件
    @FXML
    private TableView<Magazine> magazineTable;  // 左侧表格

    @FXML
    private TableColumn<Magazine, String> mdNameColumn;     //报刊名称列

    @FXML
    private TableColumn<Magazine, String> mdPriceColumn;    //周期报价列

    @FXML
    private TableColumn<Magazine, String> mdClassColumn;    //分类列

    @FXML
    private ChoiceBox<String> cycleChoiceBox;       // 订阅周期数选择盒子

    @FXML
    private ChoiceBox<String> copiesNumChoiceBox;   // 订阅份数选择盒子

    @FXML
    private Label totalPriceLabel;                  // 总金额标签

    @FXML
    private Button confirmSubscriptionButton;                   // 确认订阅按钮

    @FXML
    private ImageView magazineCover;                // 报刊封面

    @FXML
    private Label mdIdLabel;                        // 报刊编号标签

    @FXML
    private Label mdNameLabel;                      // 报刊名称标签

    @FXML
    private Label mdOfficeLabel;                    // 出版报社标签

    @FXML
    private Label mdCycleLabel;                     // 出版周期标签

    @FXML
    private Label mdPriceLabel;                     // 周期报价标签

    @FXML
    private Label mdClassLabel;                     // 分类标签

    @FXML
    private Label mdIntroLabel;                     // 内容简介标签

    // 个人信息修改面板组件
    @FXML
    private TextField userNameField;                // 用户名文本框

    @FXML
    private TextField passWordField;                // 密码文本框

    @FXML
    private TextField realNameField;                // 真实姓名文本框

    @FXML
    private TextField idCardField;                  // 身份证号文本框

    @FXML
    private TextField phoneField;                   // 联系电话文本框

    @FXML
    private TextField addressField;                 // 联系地址文本框

    @FXML
    private Button confirmChangeButton;                   // 确认修改按钮

    // 我的订单面板组件
    @FXML
    private TableView<Order> orderTable;                         // 订单表

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;           // 订单编号列

    @FXML
    private TableColumn<Order, Integer> orderMagazineIdColumn;   // 订单报刊代号列

    @FXML
    private TableColumn<Order, String> orderMagazineNameColumn; // 订单报刊名称列

    @FXML
    private TableColumn<Order, Integer> orderCycleNumsColumn;   // 订单周期数列

    @FXML
    private TableColumn<Order, Integer> orderCopiesNumsColumn;  // 订单份数列

    @FXML
    private TableColumn<Order, Integer> orderTotalPriceColumn;  // 订单金额列

    @FXML
    private PieChart statisticPriceChart;                       //饼状图

    @FXML
    private Main mainApp;               // 主程序

    private ObservableList<Magazine> magazinesData = FXCollections.observableArrayList();   // 数据库查询而得到的杂志信息

    private ObservableList<Order> orderData = FXCollections.observableArrayList();          // 数据库查询而得到的订单


    public GeneralUserController() { }

    public Main getMainApp() { return mainApp; }

    public void setMainApp(Main mainApp) { this.mainApp = mainApp; }

    /**
     * 初始化各控件，包括列表的数据绑定和函数监听,在load时会执行这些操作。
     * 这个时候mainApp还未被设置，所以是null，需要全局用户来设置用户名标签
     * 这个函数，会在构造函数结束后执行
     */
    @FXML
    private void initialize() {
        mdNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        mdPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        mdClassColumn.setCellValueFactory(cellData -> cellData.getValue().classNameProperty());
        initTable();
        initChoiceBox();
        /*
         *==================================================
         *                 !!important!!
         *       监听器要放到后面，避免未初始化的监听
         *      使用lambda表达式简化操作：https://blog.csdn.net/phcgld1314/article/details/78028995
         *==================================================
         */
        // 监听表格点击，获取点击的表格项并更新右侧详细信息
        magazineTable.getSelectionModel().selectedItemProperty().addListener(

                ((observable, oldValue, newValue) -> updateDetail(newValue))
        );
        // 监听周期选择盒子，获取订阅周期数并更新总金额
        cycleChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateTotalPrice()
        );
        // 监听份数选择盒子，获取订阅份数并更新总金额
        copiesNumChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateTotalPrice()
        );
        magazineTable.getSelectionModel().selectFirst();
        /*
         *==================================================
         *                 !!important!!
         *     这里mainApp还没有载入，所以不能使用，一直为null
         *==================================================
         */
        initUserNameLabel();
        initMyOrderPane();
    }

    /**
     * 初始化订阅报刊界面报刊显示表格
     */
    public void initTable() {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select a.id, a.coverPath, a.name, a.office, a.cycle, a.price, a.intro, b.className " +
                "from `magazine` a, `mClass` b " +
                "where a.classNumber = b.id;";
        List<Object> params = new ArrayList<>();
        try {
            List<Magazine> rawData = dbUtils.findMoreProResult(sql, params, Magazine.class);
            //System.out.println(rawData);
            magazinesData.addAll(rawData);
            magazineTable.setItems(magazinesData);
            //System.out.println(magazinesData);
        } catch (Exception e) {
            System.out.println("初始化报刊显示列表失败！");
            e.printStackTrace();
        }
    }

    /**
     * 初始化选择盒子
     */
    private void initChoiceBox() {
        // 初始化订阅周期选择盒子
        ObservableList<String> choiceBox1 = FXCollections.observableArrayList();
        // 初始化订阅份数选择盒子
        ObservableList<String> choiceBox2 = FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
            choiceBox1.add(i-1, Integer.toString(i));
            choiceBox2.add(i-1, Integer.toString(i));
        }
        cycleChoiceBox.setItems(choiceBox1);
        cycleChoiceBox.setValue("1");
        copiesNumChoiceBox.setItems(choiceBox2);
        copiesNumChoiceBox.setValue("1");
    }


    /**
     * 初始化用户名标签
     */
    private void initUserNameLabel() { userNameLabel.setText(NowUser.getUsername()); }


    /**
     * 初始化个人信息修改面板
     */
    private void initPersonInfoPane() {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `generalUser` where id = ?";
        List<Object> params = new ArrayList<>();
        params.add(NowUser.getId());
        try {
            // 找到这个用户
            GeneralUser userNow = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
            // 设置显示对应的原始数据
            userNameField.setText(userNow.getUserName());
            passWordField.setText(userNow.getPassWord());
            realNameField.setText(userNow.getRealName());
            idCardField.setText(userNow.getIdCard());
            phoneField.setText(userNow.getPhone());
            addressField.setText(userNow.getAddress());
        } catch (Exception e) {
            System.out.println("初始化用户信息失败！");
            e.printStackTrace();
        }
    }

    /**
     * 初始化订单信息修改面板
     */
    private void initMyOrderPane() {
        System.out.println("初始化我的订单页面ing...");
        // 初始化订单详情页面,这里对数字进行处理要加上.asObject但是暂时不知道为什么
        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        orderMagazineIdColumn.setCellValueFactory(cellData -> cellData.getValue().midProperty().asObject());
        orderMagazineNameColumn.setCellValueFactory(cellData -> cellData.getValue().magazineNameProperty());
        orderCopiesNumsColumn.setCellValueFactory(cellData -> cellData.getValue().copiesNumProperty().asObject());
        orderCycleNumsColumn.setCellValueFactory(cellData -> cellData.getValue().cycleNumProperty().asObject());
        orderTotalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        // 获取该用户的相关订单信息
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select a.id, b.id as uid, b.userName as userName, c.id as mid," +
                " c.name as magazineName, a.cycleNum, a.copiesNum, a.totalPrice from `order` a, " +
                "`generalUser` b, `magazine` c where a.uid = b.id and a.mid = c.id and b.id = ?;";
        List<Object> params = new ArrayList<>();
        params.add(NowUser.getId());
        try {
            List<Order> orders = dbUtils.findMoreProResult(sql, params, Order.class);
            orderData.clear();
            orderData.addAll(orders);
            orderTable.setItems(orderData);
        } catch (Exception e) {
            System.out.println("更新订单失败！");
            e.printStackTrace();
        }
        statisticPriceChart.setData(StatisticsUtil.makeNameOrderData(NowUser.getId()));
        System.out.println("初始化完毕！");
    }

    /**
     * 更新右侧详细信息的展示界面
     * @param newValue 当前报刊对象
     */
    public void updateDetail(Magazine newValue) {
        // 依次更新
        if (newValue != null) {
            File newFile = new File("src/kernel/views/css/images/" + newValue.getCoverPath());
            /*
             *==================================================
             *                 !!important!!
             *     这里的图片不能读相对路径，必须这样写
             *     如果写相对路径的话，新添加的图片在下次重启前，无法显示
             *     参考：https://blog.csdn.net/major_out/article/details/66971188?utm_source=debugrun&utm_medium=referral
             *==================================================
             */
            magazineCover.setImage(new Image(newFile.getAbsoluteFile().toURI().toString()));
            mdIdLabel.setText("报刊代号：" + newValue.getId());
            mdNameLabel.setText("报刊名称：" + newValue.getName());
            mdOfficeLabel.setText("出版报社：" + newValue.getOffice());
            mdCycleLabel.setText("出版周期：" + newValue.getCycle());
            mdPriceLabel.setText("周期报价：" + newValue.getPrice());
            mdClassLabel.setText("分类：" + newValue.getClassName());
            mdIntroLabel.setText("内容简介：" + newValue.getIntro());
            updateTotalPrice();
            System.out.println("详细信息已被更新");
        }
    }

    /**
     * 更新所选订阅报刊的总金额
     */
    private void updateTotalPrice() {
        int cycle = Integer.parseInt(cycleChoiceBox.getValue());
        int copiesNum = Integer.parseInt(copiesNumChoiceBox.getValue());
        int price = Integer.parseInt(magazineTable.getSelectionModel().getSelectedItem().getPrice());
        totalPriceLabel.setText("总金额：" +  cycle*copiesNum*price + "￥");
        System.out.println("总金额已更新");
    }


    /**
     * 跳转到订阅报刊界面
     * 由于是一开始就初始化好了的，所以不需再次初始化。
     */
    @FXML
    private void jumpToSubscriptionPane() {
        myOrderPane.setVisible(false);
        personalInfoPane.setVisible(false);
        subscriptionPane.setVisible(true);
    }

    /**
     * 跳转到个人信息修改界面
     */
    @FXML
    private void jumpToPersonInfoPane() {
        subscriptionPane.setVisible(false);
        myOrderPane.setVisible(false);
        initPersonInfoPane();
        personalInfoPane.setVisible(true);
    }

    /**
     * 跳转到我的订阅界面
     */
    @FXML
    private void jumpToMyOrderPane() {
        System.out.println("进入我的订阅界面");
        personalInfoPane.setVisible(false);
        subscriptionPane.setVisible(false);
        initMyOrderPane();
        myOrderPane.setVisible(true);
    }
    /**
     * 确认订阅功能：用户确认订阅报刊总金额，向数据库提交订单信息。
     */
    @FXML
    void handleConfirm() {
        // 弹出确认提示，如用户选择取消则返回
        if(!SysHintUtil.comfirmHint(mainApp.getPrimaryStage(), "确认您的订单信息是否正确，订单生成后无法更改！")) {
            return;
        }
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        // ？处为待替换的参数
        String sql = "insert into `order` (uid, mid, cycleNum, copiesNum, totalPrice) values(?, ?, ?, ?, ?)";
        List<Object> params = new ArrayList<>();
        // 获取当前杂志信息
        Magazine magazine = magazineTable.getSelectionModel().getSelectedItem();
        // 添加参数uid, mid, cycleNum, copiesNum, totalPrice
        params.add((NowUser.getId()));
        params.add(magazine.getId());
        params.add(cycleChoiceBox.getValue());
        params.add(copiesNumChoiceBox.getValue());
        int mcycle = Integer.parseInt(cycleChoiceBox.getValue());
        int mprice = Integer.parseInt(magazine.getPrice());
        int mcopies = Integer.parseInt(copiesNumChoiceBox.getValue());
        params.add(mcycle*mprice*mcopies);
        dbUtils.upadteByPreState(sql, params);
        SysHintUtil.successHint(mainApp.getPrimaryStage(), "订单生成成功！");
    }

    /**
     * 跳转修改个人信息界面之后，点击确认修改按钮后进入
     * 向数据库提交改变个人信息的表单
     */
    @FXML
    private void handleChange() {
        // 检查输入合法性
        String errorMessage = CheckerUtil.userUpdateCheck(NowUser.getUsername(),userNameField.getText(), passWordField.getText(),
                realNameField.getText(), idCardField.getText(), phoneField.getText(), addressField.getText());
        if(errorMessage != null) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
            return;
        }
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "update `generalUser` set userName = ?, passWord = ?, realName = ?, " +
                "idCard = ?, phone = ?, address = ? where id = ?";
        List<Object> params = new ArrayList<>();

        // 添加更改后的新值
        params.add(userNameField.getText());
        params.add(passWordField.getText());
        params.add(realNameField.getText());
        params.add(idCardField.getText());
        params.add(phoneField.getText());
        params.add(addressField.getText());
        params.add(NowUser.getId());
        try {
            dbUtils.updateByPreparedStatement(sql, params);
            SysHintUtil.successHint(mainApp.getPrimaryStage(), "更新成功");
            NowUser.setUsername(userNameField.getText());
            initUserNameLabel();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转我的订单之后，点击按名称统计订单数按钮后进入
     * 调用排序工具进行统计，并将饼状图可视化
     */
    @FXML
    void handleNameOrderPie() {
        statisticPriceChart.setData(StatisticsUtil.makeNameOrderData(NowUser.getId()));
    }

    /**
     * 跳转我的订单之后，点击按分类统计订单数按钮后进入
     * 调用排序工具进行统计，并将饼状图可视化
     */
    @FXML
    void handleClassOrderPie() {
        statisticPriceChart.setData(StatisticsUtil.makeClassOrderData(NowUser.getId()));
    }
    /**
     * 跳转我的订单之后，点击按名称统计总金额数按钮后进入
     * 调用排序工具进行统计，并将饼状图可视化
     */
    @FXML
    void handleNamePricePie() {
        statisticPriceChart.setData(StatisticsUtil.makeNamePriceData(NowUser.getUsername()));
    }

    /**
     * 跳转我的订单之后，点击按分类统计总金额数按钮后进入
     * 调用排序工具进行统计，并将饼状图可视化
     */
    @FXML
    void handleClassPricePie() {
        statisticPriceChart.setData(StatisticsUtil.makeClassPriceData(NowUser.getUsername()));
    }
    /**
     * 注销功能：用户退出，返回登录界面
     */
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/LoginWindows.fxml"));
            Parent loginView = loader.load();
            LoginViewController loginViewController = loader.getController();
            mainApp.getPrimaryStage().setScene(new Scene(loginView, 1253, 600));
            mainApp.getPrimaryStage().show();
            loginViewController.setMainApp(mainApp);
        } catch (IOException e) {
            System.out.println("退出失败！");
            e.printStackTrace();
        }
    }

}
