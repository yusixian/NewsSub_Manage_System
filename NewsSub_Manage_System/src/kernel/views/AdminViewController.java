package kernel.views;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import kernel.Main;
import kernel.NowUser;
import kernel.dao.Admin;
import kernel.dao.GeneralUser;
import kernel.dao.Magazine;
import kernel.dao.Order;
import kernel.util.*;
import kernel.util.CheckerUtil;

public class AdminViewController {
    // 菜单栏组件
    @FXML
    private Label topBarUserNameLabel;    //用户名标签

    // 五个页面面板
    @FXML
    private BorderPane magazineManagePane;           // 报刊管理面板  最开始进入这个面板

    @FXML
    private BorderPane generalUserManagePane;       // 用户管理面板

    @FXML
    private BorderPane adminManagePane;             // 管理员管理面板

    @FXML
    private BorderPane orderManagePane;             // 订单管理面板

    @FXML
    private BorderPane searchAndStatisticPane;             // 查询及统计面板

    // 报刊管理界面组件
    @FXML
    private TableView<Magazine> magazineTable;  // 左侧表格

    @FXML
    private TableColumn<Magazine, Integer> mdMidColumn;     // 报刊代号列

    @FXML
    private TableColumn<Magazine, String> mdNameColumn;     // 报刊名称列

    @FXML
    private ImageView mdManageCover;                        // 右侧详细信息封面图

    @FXML
    private Button mdManageAddMdButton;                     // 添加报刊按钮

    @FXML
    private Button mdManageDeleteMdButton;                  // 删除报刊按钮

    @FXML
    private Button mdManageUpdateMdButton;                  // 更新报刊按钮

    @FXML
    private Button mdManageCoverChooseButton;               // 封面选择按钮

    @FXML
    private TextField mdManageIdField;                      // 报刊代号文本框

    @FXML
    private TextField mdManageNameField;                    // 报刊名称文本框

    @FXML
    private TextField mdManageOfficeField;                  // 出版报社文本框

    @FXML
    private ChoiceBox<String> mdManageCycleChoiceBox;       // 出版周期数选择盒子

    @FXML
    private TextField mdManagePriceField;                   // 周期报价文本框

    @FXML
    private ChoiceBox<String> mdManageClassChoiceBox;       // 分类选择盒子

    @FXML
    private TextArea mdManageIntroArea;                     // 内容简介文本域

    private File imageFile;                                 // 报刊封面临时文件

    private ObservableList<Magazine> mdManageMdData = FXCollections.observableArrayList();   // 数据库查询而得到的报刊信息

    // 用户管理面板
    @FXML
    private TableView<GeneralUser> generalUserTable;                    // 左侧普通用户表格

    @FXML
    private TableColumn<GeneralUser, Integer> generalUserIdColumn;      // 普通用户id列

    @FXML
    private TableColumn<GeneralUser, String> generalUserNameColumn;     // 普通用户名列

    @FXML
    private Button generalUserManageAddButton;                          // 添加普通用户按钮

    @FXML
    private Button generalUserManageDeleteButton;                       // 删除普通用户按钮

    @FXML
    private Button generalUserManageUpdateButton;                       // 更新普通用户按钮

    @FXML
    private TextField generalUserIdField;                       // 用户id文本框

    @FXML
    private TextField generalUserNameField;                     // 用户名文本框

    @FXML
    private TextField generalUserPassWordField;                 // 用户密码文本框

    @FXML
    private TextField generalUserRealNameField;                 // 用户真实姓名文本框

    @FXML
    private TextField generalUserIdCardField;                   // 用户身份证号文本框

    @FXML
    private TextField generalUserPhoneField;                    // 用户联系电话文本框

    @FXML
    private TextField generalUserAddressField;                  // 用户联系地址文本框

    private ObservableList<GeneralUser> generalUsersData = FXCollections.observableArrayList();   // 数据库查询而得到的用户信息

    // 管理员管理面板
    @FXML
    private TableView<Admin> adminTable;                            // 左侧管理员表格

    @FXML
    private TableColumn<Admin, Integer> adminIdColumn;              // 管理员id列

    @FXML
    private TableColumn<Admin, String> adminUserNameColumn;         // 管理员用户名列

    @FXML
    private Button adminManageAddButton;                            // 添加管理员按钮

    @FXML
    private Button adminManageDeleteButton;                         // 删除管理员按钮

    @FXML
    private Button adminManageUpdateButton;                         // 更新管理员按钮

    @FXML
    private TextField adminIdField;                                 // 管理员id文本框

    @FXML
    private TextField adminUserNameField;                           // 管理员用户名文本框

    @FXML
    private TextField adminPassWordField;                           // 管理员密码文本框

    private ObservableList<Admin> adminsData = FXCollections.observableArrayList();   // 数据库查询而得到的管理员信息

    // 订单管理界面组件
    @FXML
    private TableView<Order> orderTable;  // 左侧表格

    @FXML
    private TableColumn<Order, Integer> orderOIdColumn;             // 订单编号列

    @FXML
    private TableColumn<Order, String> orderUserNameColumn;         // 订单用户名列

    @FXML
    private TableColumn<Order, String> orderMdNameColumn;           // 所订报刊名称列

    @FXML
    private TableColumn<Order, Integer> orderPriceColumn;            // 订单金额列

    @FXML
    private Button orderManageAddButton;                            // 添加订单按钮

    @FXML
    private Button orderManageDeleteButton;                         // 删除订单按钮

    @FXML
    private Button orderManageUpdateButton;                         // 更新订单按钮

    @FXML
    private TextField orderManageOIdField;                      // 订单编号文本框

    @FXML
    private ComboBox<String> orderManageUserNameChoiceBox;     // 订单用户名选择盒子

    @FXML
    private ComboBox<String> orderManageMdNameChoiceBox;       // 订单所订报刊名称选择盒子

    @FXML
    private ComboBox<String> orderManageCycleChoiceBox;        // 订单订阅周期数选择盒子

    @FXML
    private ComboBox<String> orderManageCopiesChoiceBox;       // 订单订阅份数选择盒子

    @FXML
    private TextField orderManagePriceField;                    // 订单金额文本框

    private ObservableList<Order> ordersdata = FXCollections.observableArrayList();   // 数据库查询而得到的表格订单信息

    // 查询与统计界面组件
    @FXML
    private ComboBox<String> searchUserNameChoiceBox;           // 查询用户名选择盒子

    @FXML
    private ComboBox<String> searchMagazineNameChoiceBox;       // 查询报刊名选择盒子

    @FXML
    private Button searchButton;                            // 查询按钮

    @FXML
    private TableView<Order> searchTable;  // 左侧表格

    @FXML
    private TableColumn<Order, Integer> searchOIdColumn;            // 订单编号列

    @FXML
    private TableColumn<Order, String> searchUserNameColumn;        // 订单用户名列

    @FXML
    private TableColumn<Order, String> searchMdNameColumn;          // 所订报刊名称列

    @FXML
    private TableColumn<Order, Integer> searchPriceColumn;          // 订单金额列

    @FXML
    private Label mostRichUserLabel;                                // 最富有用户标签

    @FXML
    private Label mostRichUserPriceLabel;                           // 最富有用户订单总金额标签

    @FXML
    private Label mostPopularMagazineLabel;                         // 最畅销报刊标签

    @FXML
    private Label mostPopularMagazinePriceLabel;                    // 最畅销报刊订单总金额标签

    @FXML
    private BarChart magazinePriceBarChart;                         // 统计的条形图
    @FXML
    CategoryAxis xAxis = new CategoryAxis();                        // 统计条形图的横轴

    private ObservableList<Order> searchOrdersdata = FXCollections.observableArrayList();   // 数据库查询而得到的订单信息

    // 各选项盒子数据
    private ObservableList<String> orderUserNameChoiceBoxdata = FXCollections.observableArrayList();
    private ObservableList<String> orderMagazineNameChoiceBoxdata = FXCollections.observableArrayList();
    private ObservableList<String> searchUserNameChoiceBoxdata = FXCollections.observableArrayList();
    private ObservableList<String> searchMagazineNameChoiceBoxdata = FXCollections.observableArrayList();

    private Main mainApp;               // 主程序

    public AdminViewController() { System.out.println("构造函数"); }

    public Main getMainApp() { return mainApp; }

    public void setMainApp(Main mainApp) { this.mainApp = mainApp; }

    /**
     * 初始化各控件，包括列表的数据绑定和函数监听,在load时会执行这些操作。
     * 这个时候mainApp还未被设置，所以是null，需要全局用户来设置用户名标签
     * 这个函数，会在构造函数结束后执行
     */
    @FXML
    private void initialize() {
        System.out.println("初始化页面…");
        // 初始化表格
        // 报刊列表
        mdNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        mdMidColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        magazineTable.getSelectionModel().selectedItemProperty().addListener(       // 监听表格点击，获取点击的表格项并更新右侧详细信息
                ((observable, oldValue, newValue) -> mdUpdateDetail(newValue))
        );
        initMdManageTable();            // 初始化报刊管理界面的表格
        // 用户列表
        generalUserIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        generalUserNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        generalUserTable.getSelectionModel().selectedItemProperty().addListener(    // 监听表格点击，获取点击的表格项并更新右侧详细信息
                ((observable, oldValue, newValue) -> generalUserUpdateDetail(newValue))
        );
        initGeneralUserTable();            // 初始化报刊管理界面的表格
        // 管理员列表
        adminIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        adminUserNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        adminTable.getSelectionModel().selectedItemProperty().addListener(          // 监听表格点击，获取点击的表格项并更新右侧详细信息
                ((observable, oldValue, newValue) -> adminUpdateDetail(newValue))
        );
        initAdminTable();            // 初始化管理员管理界面的表格
        // 订单列表
        orderOIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        orderUserNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        orderMdNameColumn.setCellValueFactory(cellData -> cellData.getValue().magazineNameProperty());
        orderPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        orderTable.getSelectionModel().selectedItemProperty().addListener(          // 监听表格点击，获取点击的表格项并更新右侧详细信息
                ((observable, oldValue, newValue) -> orderUpdateDetail(newValue))
        );
        initOrderTable();            // 初始化订单管理界面的表格
        // 查询列表
        searchOIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        searchUserNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        searchMdNameColumn.setCellValueFactory(cellData -> cellData.getValue().magazineNameProperty());
        searchPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        // 这个不用监听
        initSearchTable();            // 初始化订单管理界面的表格
        System.out.println("初始化表格完毕…");

        // 初始化选择盒子
        initMdManageChoiceBox();        // 初始化报刊管理界面的选择盒子
        initOrderManageChoiceBox();        // 初始化订单管理界面的选择盒子
        initSearchManageChoiceBox();        // 初始化查询及统计界面的选择盒子

        // 初始化用户名标签
        initTopBarUserNameLabel();

        // 初始化查询与统计面板
        initSearchAndStatisticPane();
    }

    /**
     * 初始化菜单栏用户名标签
     */
    private void initTopBarUserNameLabel() { topBarUserNameLabel.setText(NowUser.getUsername()); }


    /**
     * 初始化查询与统计面板
     */
    private void initSearchAndStatisticPane() {
        initSearchTable();

        Map<String, Object> man = StatisticsUtil.mostRichUser();
        System.out.println(man);
        mostRichUserLabel.setText(man.get("userName").toString());
        System.out.println(mostRichUserLabel.getText());
        mostRichUserPriceLabel.setText(man.get("totalPrice").toString() + "￥");
        System.out.println(mostRichUserPriceLabel.getText());

        Map<String, Object> magazine = StatisticsUtil.mostPopularMagazine();
        mostPopularMagazineLabel.setText(magazine.get("magazineName").toString());
        mostPopularMagazinePriceLabel.setText(magazine.get("totalPrice").toString() + "￥");
        handleMdNamePriceStatistic();
    }

    /**
     * 初始化用户管理界面左侧用户显示列表
     */
    private void initGeneralUserTable() {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `generalUser`;";
        List<Object> params = new ArrayList<>();
        try {
            List<GeneralUser> generalUsers = dbUtils.findMoreProResult(sql, params, GeneralUser.class);
            generalUsersData.clear();
            generalUsersData.addAll(generalUsers);
            generalUserTable.setItems(generalUsersData);
            generalUserTable.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化报刊管理界面左侧报刊显示列表
     */
    public void initMdManageTable() {
        System.out.println("initmd");
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select a.id, a.coverPath, a.name, a.office, a.cycle, a.price, a.intro, b.className " +
                "from `magazine` a, `mClass` b " +
                "where a.classNumber = b.id;";
        List<Object> params = new ArrayList<>();
        try {
            List<Magazine> rawData = dbUtils.findMoreProResult(sql, params, Magazine.class);
            mdManageMdData.clear();             // 重要 不然添加杂志会加一堆
            mdManageMdData.addAll(rawData);
            magazineTable.setItems(mdManageMdData);
            magazineTable.getSelectionModel().selectFirst();
        } catch (Exception e) {
            System.out.println("初始化报刊显示列表失败！");
            e.printStackTrace();
        }
    }

    /**
     * 初始化管理员管理界面左侧管理员显示列表
     */
    private void initAdminTable() {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `admin`;";
        List<Object> params = new ArrayList<>();
        try {
            List<Admin> admins = dbUtils.findMoreProResult(sql, params, Admin.class);
            adminsData.clear();
            adminsData.addAll(admins);
            adminTable.setItems(adminsData);
            adminTable.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化订单管理界面左侧订单显示列表
     */
    private void initOrderTable() {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select a.id as id, " +
                "a.uid, b.userName as userName, " +
                "a.mid, c.name as magazineName, " +
                "a.cycleNum, a.copiesNum, a.totalPrice " +
                "from `order` a, `generalUser` b, `magazine` c " +
                "where a.uid = b.id and a.mid = c.id;";
        List<Object> params = new ArrayList<>();
        try {
            List<Order> orders = dbUtils.findMoreProResult(sql, params, Order.class);
            ordersdata.clear();
            ordersdata.addAll(orders);
            orderTable.setItems(ordersdata);
            orderTable.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化查询管理界面左侧订单显示列表为全部订单
     */
    private void initSearchTable() {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select a.id as id, " +
                "a.uid, b.userName as userName, " +
                "a.mid, c.name as magazineName, " +
                "a.cycleNum, a.copiesNum, a.totalPrice " +
                "from `order` a, `generalUser` b, `magazine` c " +
                "where a.uid = b.id and a.mid = c.id;";
        List<Object> params = new ArrayList<>();
        try {
            List<Order> orders = dbUtils.findMoreProResult(sql, params, Order.class);
            searchOrdersdata.clear();
            searchOrdersdata.addAll(orders);
            searchTable.setItems(searchOrdersdata);
            searchTable.getSelectionModel().selectFirst();
        } catch (Exception e) {
            System.out.println("初始化订单表格失败！");
            e.printStackTrace();
        }
    }

    /**
     * 初始化字符串形式的选择盒子，通过sql语句选择对应其数据库信息
     * @param sql 数据库选择语句
     * @param box 选择盒子
     * @param items 选择盒子的数据
     */
    private void initBox(String sql, ComboBox<String> box, ObservableList<String> items) {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        List<Object> params = new ArrayList<>();
        try {
            items.clear();
            List<Map<String, Object>> names = dbUtils.findModeResult(sql, params);
            for (Map<String, Object> name : names) {
                items.add(name.get("name").toString());
            }
            box.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化报刊管理界面选择盒子
     * 这种的就直接加了
     */
    private void initMdManageChoiceBox() {
        // 初始化出版周期选择盒子
        ObservableList<String> choiceBox1 = FXCollections.observableArrayList();
        choiceBox1.add("旬刊");
        choiceBox1.add("半月刊");
        choiceBox1.add("月刊");
        choiceBox1.add("双月刊");
        choiceBox1.add("季刊");
        choiceBox1.add("年刊");
        // 初始化分类选择盒子
        ObservableList<String> choiceBox2 = FXCollections.observableArrayList();
        choiceBox2.add("生活");
        choiceBox2.add("故事");
        choiceBox2.add("地理");
        choiceBox2.add("二次元");
        choiceBox2.add("未分类");
        mdManageCycleChoiceBox.setItems(choiceBox1);
        mdManageCycleChoiceBox.setValue("旬刊");
        mdManageClassChoiceBox.setItems(choiceBox2);
        mdManageClassChoiceBox.setValue("生活");
    }

    /**
     * 初始订单管理界面选择盒子
     */
    private void initOrderManageChoiceBox() {
        initBox("select userName as name from `generalUser`", orderManageUserNameChoiceBox, orderUserNameChoiceBoxdata);  // 初始化用户名盒子为所有已有用户名
        initBox("select name from `magazine`", orderManageMdNameChoiceBox, orderMagazineNameChoiceBoxdata);                 // 初始化报刊名选择盒子为所有报刊名称
        // 初始化周期数选择盒子
        ObservableList<String> cycles = FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
            cycles.add(Integer.toString(i));
        }
        orderManageCycleChoiceBox.setItems(cycles);

        // 初始化份数选择盒子
        ObservableList<String> temp = FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
            temp.add(Integer.toString(i));
        }
        orderManageCopiesChoiceBox.setItems(temp);
    }

    /**
     * 初始化查询及统计界面选择盒子
     */
    private void initSearchManageChoiceBox() {
        initBox("select userName as name from `generalUser`", searchUserNameChoiceBox, searchUserNameChoiceBoxdata);  // 初始化用户名盒子为所有已有用户名
        initBox("select name from `magazine`", searchMagazineNameChoiceBox, searchMagazineNameChoiceBoxdata);
    }

    /**
     * 更新报刊管理界面右侧报刊信息的展示
     * @param nowmd 当前报刊对象
     */
    public void mdUpdateDetail(Magazine nowmd) {
        if(nowmd != null) {
            System.out.println("mdupdate");
            // 依次更新
            File newFile = new File("src/kernel/views/css/images/" + nowmd.getCoverPath());
            /*
             *==================================================
             *                 !!important!!
             *     这里的图片不能读相对路径，必须这样写
             *     如果写相对路径的话，新添加的图片在下次重启前，无法显示
             *     参考：https://blog.csdn.net/major_out/article/details/66971188?utm_source=debugrun&utm_medium=referral
             *==================================================
             */
            mdManageCover.setImage(new Image(newFile.getAbsoluteFile().toURI().toString()));
            mdManageIdField.setText(""+nowmd.getId());
            mdManageNameField.setText(nowmd.getName());
            mdManageOfficeField.setText(nowmd.getOffice());
            mdManageCycleChoiceBox.setValue(nowmd.getCycle());
            mdManagePriceField.setText(nowmd.getPrice());
            mdManageClassChoiceBox.setValue(nowmd.getClassName());
            mdManageIntroArea.setText(nowmd.getIntro());
            imageFile = new File("src/kernel/views/css/images/" + nowmd.getCoverPath());
            System.out.println("报刊详细信息已被更新");
        }
    }

    /**
     * 更新用户管理界面右侧管理信息的展示
     * @param newValue 当前用户对象
     */
    private void generalUserUpdateDetail(GeneralUser newValue) {
        if(newValue != null) {
            generalUserIdField.setText(""+newValue.getId());
            generalUserNameField.setText(newValue.getUserName());
            generalUserPassWordField.setText(newValue.getPassWord());
            generalUserRealNameField.setText(newValue.getRealName());
            generalUserIdCardField.setText(newValue.getIdCard());
            generalUserPhoneField.setText(newValue.getPhone());
            generalUserAddressField.setText(newValue.getAddress());
            System.out.println("用户详细信息已被更新");
        }
    }

    /**
     * 更新管理员管理界面右侧管理信息的展示
     * @param newValue 当前管理员对象
     */
    private void adminUpdateDetail(Admin newValue) {
        if (newValue != null) {
            // 依次更新
            adminIdField.setText(""+newValue.getId());
            adminUserNameField.setText(newValue.getUserName());
            adminPassWordField.setText(newValue.getPassWord());
            System.out.println("管理员详细信息已被更新");
        }
    }

    /**
     * 更新订单管理界面右侧管理信息的展示
     * @param newValue 当前订单对象
     */
    private void orderUpdateDetail(Order newValue) {
        if (newValue != null) {
            // 依次更新
            orderManageOIdField.setText((""+newValue.getId()));
            orderManageUserNameChoiceBox.setValue(newValue.getUserName());
            orderManageMdNameChoiceBox.setValue(newValue.getMagazineName());
            orderManageCycleChoiceBox.setValue(""+newValue.getCycleNum());
            orderManageCopiesChoiceBox.setValue(""+newValue.getCopiesNum());
            orderManagePriceField.setText((""+newValue.getTotalPrice()));
            System.out.println("订单详细信息已被更新");
        }
    }

    /**
     * 更新查询管理界面右侧管理信息的展示
     * @param newValue 当前订单对象
     */
    private void searchUpdateDetail(Order newValue) {
        if (newValue != null) {
            // 依次更新
            orderManageOIdField.setText((""+newValue.getId()));
            orderManageUserNameChoiceBox.setValue(newValue.getUserName());
            orderManageMdNameChoiceBox.setValue(newValue.getMagazineName());
            orderManageCycleChoiceBox.setValue(""+newValue.getCycleNum());
            orderManageCopiesChoiceBox.setValue(""+newValue.getCopiesNum());
            orderManagePriceField.setText((""+newValue.getTotalPrice()));
            System.out.println("订单详细信息已被更新");
        }
    }

    /**
     * 报刊管理界面句柄：选择封面图，用户选择封面图上传，上传完毕后替换文件
     * 涉及用户文件操作，重要函数
     */
    @FXML
    void handleMdCoverChoose() {
        System.out.println("点击了封面选择按钮");
        imageFile = ImageUtil.choose(mainApp);
        // 这里会输出该图片的路径
        System.out.println();
        // 替换图片
        try {
            if (imageFile != null) {
                String localUrl = imageFile.toURI().toURL().toString();
                mdManageCover.setImage(new Image(localUrl, true));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 报刊管理界面句柄：检查合法性后以当前报刊信息添加新的报刊信息
     * 添加的时候要同时将封面添加到css的image文件夹中。注意要用特殊的路径
     * 才能读取新添加的文件。这一点很重要！
     */
    @FXML
    private void handleNewMagazine() {
        String errorMessage = CheckerUtil.magazineSignUpCheck(mdManageIdField.getText(),
                imageFile,
                mdManageNameField.getText(),
                mdManageOfficeField.getText(),
                mdManageCycleChoiceBox.getValue(),
                mdManagePriceField.getText(),
                mdManageClassChoiceBox.getValue(),
                mdManageIntroArea.getText());
        if (errorMessage == null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "select id from `mClass` where className = ?";;
            List<Object> params = new ArrayList<>();
            params.add(mdManageClassChoiceBox.getValue());
            try {
                Map<String, Object> id = dbUtils.findSimpleResult(sql, params);
                sql = "insert into `magazine`(id, coverPath, name, office, cycle, price, intro, classNumber) " +
                        "values(?, ?, ?, ?, ?, ?, ?, ?)";
                params.clear();

                params.add(mdManageIdField.getText());
                params.add(imageFile.getName());
                params.add(mdManageNameField.getText());
                params.add(mdManageOfficeField.getText());
                params.add(mdManageCycleChoiceBox.getValue());
                params.add(mdManagePriceField.getText());
                params.add(mdManageIntroArea.getText());
                params.add(id.get("id"));

                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "添加报刊成功");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 复制文件到image文件夹
            ImageUtil.save(imageFile);
            initMdManageTable();
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }

    /**
     * 报刊管理界面：删除当前报刊信息
     * 删除报刊数据的同时删除images中的图片文件。
     */
    @FXML
    private void handleDeleteMagazine() {
        // 获取到所选项
        int index = magazineTable.getSelectionModel().getFocusedIndex();
        Magazine magazine = magazineTable.getSelectionModel().getSelectedItem();

        // 进行删除操作
        if (magazine != null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "delete from `magazine` where id = ?";
            List<Object> params = new ArrayList<>();
            params.add(magazine.getId());

            try {
                if (SysHintUtil.comfirmHint(mainApp.getPrimaryStage(), "您确认要删除这个报刊吗？一旦删除后无法恢复！")) {
                    dbUtils.updateByPreparedStatement(sql, params);
                    /*
                     *==================================================
                     *                 !!important!!
                     *     注意这里移动后会进行一个update所以不能用
                     *     imageFile.getPath()
                     *==================================================
                     */
                    magazineTable.getItems().remove(index);
                    ImageUtil.delete(magazine.getCoverPath());
                    SysHintUtil.successHint(mainApp.getPrimaryStage(), "删除成功!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 报刊管理界面：更新当前报刊信息
     * 报刊更新的同时要记得保存新图片和删除老图片
     */
    @FXML
    private void handleUpdateMagazine() {
        // 获取旧报刊信息
        Magazine oldInfo = magazineTable.getSelectionModel().getSelectedItem();
        String errorMessage;
        // 将id信息转化为整型
        String foo = mdManageIdField.getText();
        if (foo == null || foo.length() == 0) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id为空");
            return;
        } else {
            try {
                Integer.parseInt(foo);
            } catch (Exception e) {
                SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id应只含有数字");
                return;
            }
        }

        if (imageFile == null) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "该图片已被使用");
            return;
        }

        Magazine newInfo = new Magazine(Integer.parseInt(mdManageIdField.getText()),
                imageFile.getName(),
                mdManageNameField.getText(),
                mdManageOfficeField.getText(),
                mdManageCycleChoiceBox.getValue(),
                mdManagePriceField.getText(),
                mdManageIntroArea.getText(),
                mdManageClassChoiceBox.getValue());
        errorMessage = CheckerUtil.magazineUpdateCheck(oldInfo, newInfo); // 检查更新信息合法性

        // 更新数据库
        if (errorMessage == null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            // 寻找分类名对应的编号
            String sql = "select id from `mClass` where className = ?";
            List<Object> params = new ArrayList<>();
            params.add(newInfo.getClassName());
            System.out.println(newInfo.getClassName());

            try {
                Map<String, Object> id = dbUtils.findSimpleResult(sql, params);     //这个是分类名对应的编号
                sql = "update `magazine` set coverPath = ?, name = ?, office = ?, cycle = ?, price = ?, intro = ?, classNumber = ? " +
                        "where id = ?;";
                params.clear();
                params.add(newInfo.getCoverPath());
                params.add(newInfo.getName());
                params.add(newInfo.getOffice());
                params.add(newInfo.getCycle());
                params.add(newInfo.getPrice());
                params.add(newInfo.getIntro());
                params.add(id.get("id"));   // classNumber
                params.add(newInfo.getId());
                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "更新成功");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            /*
             *==================================================
             *                 !!important!!
             *     注意这里为了防止在新图片在image的情况，一定
             *     要注意顺序，好吧实际上这种情况并不会出现，如果
             *     不是用户手动向static文件夹中添加图片
             *==================================================
             */
            // 添加新图片,删除旧图片.
            if (!oldInfo.getCoverPath().equals(newInfo.getCoverPath())){
                // 如果新图片跟旧图片路径不一样
                ImageUtil.save(imageFile);
                ImageUtil.delete(oldInfo.getCoverPath());
            } else {    // 新图片跟旧图片路径一样
                // 啥也不用干
            }
            initMdManageTable();
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }

    /**
     * 用户管理界面：检查合法性后添加新的报刊信息
     */
    @FXML
    private void handleNewUser() {
        String errorMessage = CheckerUtil.generalUserManageSignUpCheck(generalUserIdField.getText(),
                generalUserNameField.getText(),
                generalUserPassWordField.getText(),
                generalUserRealNameField.getText(),
                generalUserIdCardField.getText(),
                generalUserPhoneField.getText(),
                generalUserAddressField.getText());
        if (errorMessage == null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "insert into `generalUser`(id, userName, passWord, realName, idCard, phone, address) " +
                    "values(?, ?, ?, ?, ?, ?, ?)";
            List<Object> params = new ArrayList<>();
            params.add(Integer.parseInt(generalUserIdField.getText()));
            params.add(generalUserNameField.getText());
            params.add(generalUserPassWordField.getText());
            params.add(generalUserRealNameField.getText());
            params.add(generalUserIdCardField.getText());
            params.add(generalUserPhoneField.getText());
            params.add(generalUserAddressField.getText());

            try {
                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "添加成功");
                initGeneralUserTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }

    /**
     * 用户管理界面：删除当前用户信息
     */
    @FXML
    private void handleDeleteGeneralUser() {
        // 获取到所选项
        int index = generalUserTable.getSelectionModel().getFocusedIndex();
        GeneralUser generalUser = generalUserTable.getSelectionModel().getSelectedItem();
        // 进行删除操作
        if (generalUser != null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "delete from `generalUser` where id = ?";
            List<Object> params = new ArrayList<>();
            params.add(generalUser.getId());

            try {
                if (SysHintUtil.comfirmHint(mainApp.getPrimaryStage(), "您确认要删除这个用户吗？一旦删除后无法恢复，该用户的订单也会被一并删掉！")) {
                    dbUtils.updateByPreparedStatement(sql, params);
                    generalUserTable.getItems().remove(index);  // 从左侧表格中移除
                    SysHintUtil.successHint(mainApp.getPrimaryStage(), "删除成功!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用户管理界面：更新当前用户信息
     */
    @FXML
    private void handleUpdateGeneralUser() {
        System.out.println("点击了更新按钮");
        // 获取旧用户信息
        GeneralUser oldInfo = generalUserTable.getSelectionModel().getSelectedItem();
        String errorMessage;
        // 将id信息转化为整型
        String foo = generalUserIdField.getText();
        if (foo == null || foo.length() == 0) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id为空");
            return;
        } else {
            try {
                Integer.parseInt(foo);
            } catch (Exception e) {
                SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id应只含有数字");
                return;
            }
        }

        GeneralUser newInfo = new GeneralUser(Integer.parseInt(generalUserIdField.getText()),
                generalUserNameField.getText(),
                generalUserPassWordField.getText(),
                generalUserRealNameField.getText(),
                generalUserIdCardField.getText(),
                generalUserPhoneField.getText(),
                generalUserAddressField.getText());
        errorMessage = CheckerUtil.generalUserUpdateCheck(oldInfo, newInfo); // 检查更新信息合法性

        // 确认无误，更新数据库
        if (errorMessage == null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "update `generalUser` set userName = ?, passWord = ?, realName = ?, idCard = ?, phone = ?, address = ? " +
                    "where id = ?;";
            try {
                List<Object> params = new ArrayList<>();
                params.add(newInfo.getUserName());
                params.add(newInfo.getPassWord());
                params.add(newInfo.getRealName());
                params.add(newInfo.getIdCard());
                params.add(newInfo.getPhone());
                params.add(newInfo.getAddress());
                params.add(newInfo.getId());
                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "更新成功");
            } catch (SQLException e) {
                System.out.println("更新失败！");
                e.printStackTrace();
            }
            initGeneralUserTable();
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }

    /**
     * 管理员管理界面：添加当前管理员信息
     */
    @FXML
    private void handleAddAdmin() {
        String errorMessage = CheckerUtil.adminSignUpCheck(adminIdField.getText(),
                adminUserNameField.getText(),
                adminPassWordField.getText() );
        if (errorMessage == null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "insert into `admin`(id, userName, passWord) " +
                    "values(?, ?, ?)";
            List<Object> params = new ArrayList<>();
            params.add(Integer.parseInt(adminIdField.getText()));
            params.add(adminUserNameField.getText());
            params.add(adminPassWordField.getText());
            try {
                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "添加成功");
                initAdminTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }

    /**
     * 管理员管理界面：删除当前管理员信息
     */
    @FXML
    private void handleDeleteAdmin() {
        // 获取到所选项
        int index = adminTable.getSelectionModel().getFocusedIndex();
        Admin admin = adminTable.getSelectionModel().getSelectedItem();
        // 进行删除操作
        if (admin != null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "delete from `admin` where id = ?";
            List<Object> params = new ArrayList<>();
            params.add(admin.getId());
            try {
                if (SysHintUtil.comfirmHint(mainApp.getPrimaryStage(), "您真的要删除这个管理员吗？一旦删除后无法恢复！")) {
                    dbUtils.updateByPreparedStatement(sql, params);
                    adminTable.getItems().remove(index);  // 从左侧表格中移除
                    SysHintUtil.successHint(mainApp.getPrimaryStage(), "管理员删除成功!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 管理员管理界面：更新当前管理员信息
     */
    @FXML
    private void handleUpdateAdmin() {
        System.out.println("点击了更新按钮");
        // 获取旧管理员信息
        Admin oldInfo = adminTable.getSelectionModel().getSelectedItem();
        String errorMessage;
        // 将id信息转化为整型
        String foo = adminIdField.getText();
        if (foo == null || foo.length() == 0) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id为空");
            return;
        } else {
            try {
                Integer.parseInt(foo);
            } catch (Exception e) {
                SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id应只含有数字");
                return;
            }
        }

        Admin newInfo = new Admin(Integer.parseInt(adminIdField.getText()),
                adminUserNameField.getText(),
                adminPassWordField.getText());
        errorMessage = CheckerUtil.adminInfoUpdateCheck(oldInfo, newInfo); // 检查更新信息合法性

        // 确认无误，更新数据库
        if (errorMessage == null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "update `admin` set userName = ?, passWord = ? " +
                    "where id = ?;";
            try {
                List<Object> params = new ArrayList<>();

                params.add(newInfo.getUserName());
                params.add(newInfo.getPassWord());
                params.add(newInfo.getId());

                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "更新成功");
            } catch (SQLException e) {
                System.out.println("更新失败！");
                e.printStackTrace();
            }
            initAdminTable();
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }
    /**
     * 订单管理界面：添加当前订单信息
     */
    @FXML
    private void handleAddOrder() {

        String errorMessage = CheckerUtil.orderSignUpCheck(
                orderManageOIdField.getText(),
                orderManageUserNameChoiceBox.getEditor().getText(),
                orderManageMdNameChoiceBox.getEditor().getText(),
                orderManageCycleChoiceBox.getEditor().getText(),
                orderManageCopiesChoiceBox.getEditor().getText(),
                orderManagePriceField.getText()
        );
        if (errorMessage == null) {
            Integer uid = null;
            Integer mid = null;

            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();

            // 寻找用户名对应的用户id
            String sql = "select id from `generalUser` where userName = ?";
            List<Object> params = new ArrayList<>();
            params.add(orderManageUserNameChoiceBox.getEditor().getText());
            try {
                Map<String, Object> result = dbUtils.findSimpleResult(sql, params);
                uid = (Integer) result.get("id");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 寻找报刊名对应的报刊id
            sql = "select id from `magazine` where name = ?";
            params.clear();
            params.add(orderManageMdNameChoiceBox.getEditor().getText());
            try {
                Map<String, Object> result = dbUtils.findSimpleResult(sql, params);
                mid = (Integer) result.get("id");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 添加新订单
            sql = "insert into `order`(id, uid, mid, cycleNum, copiesNum, totalPrice) " +
                    "values(?, ?, ?, ?, ?, ?)";
            params.clear();
            params.add(Integer.parseInt(orderManageOIdField.getText()));
            params.add(uid);
            params.add(mid);
            params.add(orderManageCycleChoiceBox.getEditor().getText());
            params.add(orderManageCopiesChoiceBox.getEditor().getText());
            params.add(Integer.parseInt(orderManagePriceField.getText()));
            try {
                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "订单添加成功");
                initOrderTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }

    /**
     * 订单管理界面：删除当前订单信息
     */
    @FXML
    private void handleDeleteOrder() {
        int index = orderTable.getSelectionModel().getFocusedIndex();
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "delete from `order` where id = ?";
            List<Object> params = new ArrayList<>();
            params.add(order.getId());
            try {
                if (SysHintUtil.comfirmHint(mainApp.getPrimaryStage(), "您确定要删除这个订单吗？")) {
                    dbUtils.updateByPreparedStatement(sql, params);
                    SysHintUtil.successHint(mainApp.getPrimaryStage(), "删除订单成功");
                    orderTable.getItems().remove(index);    //从右侧表项中删除
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 订单管理界面：更新当前订单信息
     */
    @FXML
    private void handleUpdateOrder() {
        Order oldInfo = orderTable.getSelectionModel().getSelectedItem();
        String errorMessage = CheckerUtil.orderUpdateCheck(
                oldInfo,
                orderManageOIdField.getText(),
                orderManageUserNameChoiceBox.getEditor().getText(),
                orderManageMdNameChoiceBox.getEditor().getText(),
                orderManageCycleChoiceBox.getEditor().getText(),
                orderManageCopiesChoiceBox.getEditor().getText(),
                orderManagePriceField.getText()
        );
        if (errorMessage == null) {
            Integer uid = null;
            Integer mid = null;

            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();

            // 寻找用户名对应的用户id
            String sql = "select id from `generalUser` where userName = ?";
            List<Object> params = new ArrayList<>();
            params.add(orderManageUserNameChoiceBox.getEditor().getText());
            try {
                Map<String, Object> result = dbUtils.findSimpleResult(sql, params);
                uid = (Integer) result.get("id");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 寻找报刊名对应的报刊id
            sql = "select id from `magazine` where name = ?";
            params.clear();
            params.add(orderManageMdNameChoiceBox.getEditor().getText());
            try {
                Map<String, Object> result = dbUtils.findSimpleResult(sql, params);
                mid = (Integer) result.get("id");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            sql = "update `order` set uid = ?, mid = ?, cycleNum = ?, copiesNum = ?, totalPrice = ? " +
                    "where id = ?";
            params.clear();
            params.add(uid);
            params.add(mid);
            params.add(orderManageCycleChoiceBox.getEditor().getText());
            params.add(orderManageCopiesChoiceBox.getEditor().getText());
            params.add(Integer.parseInt(orderManagePriceField.getText()));
            params.add(Integer.parseInt(orderManageOIdField.getText()));
            try {
                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "更新订单信息成功");
                initOrderTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }

    /**
     * 查询及统计界面：查询当前所选用户、报刊信息
     */
    @FXML
    private void handleSearchData() {
        // 获取所选项
        String uName = searchUserNameChoiceBox.getEditor().getText();
        String mName = searchMagazineNameChoiceBox.getEditor().getText();

        //进行搜索的过滤
        ObservableList<Order> searchData = FXCollections.observableArrayList();
        if(mName.length() != 0 || uName.length() != 0){
            for (Order order : searchOrdersdata) {        // 遍历所有订单数据
                if (mName.length() != 0 && !order.getMagazineName().equals(mName)) {    // 若有选择用户名，则不是该用户名的不要
                    continue;
                }
                if (uName.length() != 0 && !order.getUserName().equals(uName)) {        // 若有选择报刊名，则不是该报刊的不要
                    continue;
                }
                searchData.add(order);
            }
        } else searchData = searchOrdersdata;
        searchTable.setItems(searchData);       // 将过滤后的数据显示出来
    }

    /**
     * 查询及统计界面：按报刊名称统计订单金额
     */
    @FXML
    private void handleMdNamePriceStatistic() {
        magazinePriceBarChart.getData().clear();
        // 获取所有报刊名称与对应总金额，返回字典组成的列表
        List<Map<String, Object>> foo = StatisticsUtil.commonBarChartQuery("select * from `semagazinereview`;");
        ObservableList<String> names = FXCollections.observableArrayList();
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("总金额");
        for (Map<String, Object> map : foo) {
//            System.out.println(map);
            names.add(map.get("magazineName").toString());
            series1.getData().add(new XYChart.Data<>(map.get("magazineName"), map.get("totalPrice")));
        }
        xAxis.getCategories().clear();
        xAxis.setCategories(names);
        magazinePriceBarChart.getData().addAll(series1);
    }

    /**
     * 查询及统计界面：按分类统计订单金额
     */
    @FXML
    private void handleMClassPriceStatistic() {
        magazinePriceBarChart.getData().clear();
        // 获取所有报刊名称与对应总金额，返回字典组成的列表
        List<Map<String, Object>> foo = StatisticsUtil.commonBarChartQuery("select * from `seclassreview`;");
        ObservableList<String> names = FXCollections.observableArrayList();
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("总金额");
        for (Map<String, Object> map : foo) {
//            System.out.println(map);
            names.add(map.get("className").toString());
            series1.getData().add(new XYChart.Data<>(map.get("className"), map.get("totalPrice")));
        }
        xAxis.getCategories().clear();
        xAxis.setCategories(names);
        magazinePriceBarChart.getData().addAll(series1);
    }

    /**
     * 跳转到报刊管理界面
     */
    @FXML
    private void jumpToMagazineManagePane() {
        generalUserManagePane.setVisible(false);
        adminManagePane.setVisible(false);
        orderManagePane.setVisible(false);
        searchAndStatisticPane.setVisible(false);
        initMdManageTable();
        magazineManagePane.setVisible(true);
    }

    /**
     * 跳转到用户管理界面
     */
    @FXML
    private void jumpToGeneralUserManagePane() {
        magazineManagePane.setVisible(false);
        adminManagePane.setVisible(false);
        orderManagePane.setVisible(false);
        searchAndStatisticPane.setVisible(false);
        initGeneralUserTable();
        generalUserManagePane.setVisible(true);
    }

    /**
     * 跳转到管理员管理界面
     */
    @FXML
    private void jumpToAdminManagePane() {
        magazineManagePane.setVisible(false);
        generalUserManagePane.setVisible(false);
        orderManagePane.setVisible(false);
        searchAndStatisticPane.setVisible(false);
        initAdminTable();
        adminManagePane.setVisible(true);
    }

    /**
     * 跳转到订单管理界面
     */
    @FXML
    private void jumpToOrderManagePane() {
        magazineManagePane.setVisible(false);
        generalUserManagePane.setVisible(false);
        adminManagePane.setVisible(false);
        searchAndStatisticPane.setVisible(false);
        initOrderTable();
        orderManagePane.setVisible(true);
    }

    /**
     * 跳转到查询及统计界面
     */
    @FXML
    private void jumpToSearchAndStatisticPane() {
        magazineManagePane.setVisible(false);
        generalUserManagePane.setVisible(false);
        adminManagePane.setVisible(false);
        orderManagePane.setVisible(false);
        initSearchAndStatisticPane();
        searchAndStatisticPane.setVisible(true);
    }

    /**
     * 注销功能：管理员退出，返回登录界面
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
