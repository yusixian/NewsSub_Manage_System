package kernel.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kernel.Main;

import java.util.Optional;

public class SysHintUtil {

    /**
     * 弹出一个通用的错误信息提示框
     * 参考博客：https://www.cnblogs.com/shiningWish/p/6213710.html
     * @param stage 对话框所处页面
     * @param message 对话框的信息
     */
    public static void errorInfoHint(Stage stage, String message){
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("错误");
        ImageView menhera = new ImageView(Main.class.getResource("views/css/images/System/error.png").toString());
        menhera.setFitHeight(125);
        menhera.setPreserveRatio(true);
        _alert.setGraphic(menhera);
        _alert.setHeaderText("您可能有些信息填错了！");
        _alert.setContentText(message);
        _alert.initOwner(stage);
        _alert.show();
    }

    /**
     * 弹出一个通用的确定对话框
     * 参考博客：https://www.cnblogs.com/shiningWish/p/6213710.html
     * @param stage 对话框所处页面
     * @param message 对话框的信息
     * @return 用户点击了是或否
     */
    public static boolean comfirmHint(Stage stage, String message) {
        // 按钮部分可以使用预设的也可以像这样自己 new 一个
        Alert _alert = new Alert(Alert.AlertType.CONFIRMATION,message,new ButtonType("取消", ButtonBar.ButtonData.NO),
                new ButtonType("确定", ButtonBar.ButtonData.YES));
        // 设置窗口的标题
        ImageView menhera = new ImageView(Main.class.getResource("views/css/images/System/comfirm.png").toString());
        menhera.setFitHeight(125);
        menhera.setPreserveRatio(true);
        _alert.setGraphic(menhera);

        _alert.setTitle("确认");
        _alert.setHeaderText("请注意!");
        // 设置对话框的 icon 图标，参数是主窗口的 stage
        _alert.initOwner(stage);
        // showAndWait() 将在对话框消失以前不会执行之后的代码
        Optional<ButtonType> _buttonType = _alert.showAndWait();
        // 根据点击结果返回
        if(_buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 弹出一个通用的成功信息提示框
     * 参考博客：https://www.cnblogs.com/shiningWish/p/6213710.html
     * @param stage 对话框所处页面
     * @param message 对话框的信息
     */
    public static void successHint(Stage stage, String message) {
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        ImageView menhera = new ImageView(Main.class.getResource("views/css/images/System/success.png").toString());
        menhera.setFitHeight(125);
        menhera.setPreserveRatio(true);
        _alert.setGraphic(menhera);
        _alert.setTitle("恭喜");
        _alert.setHeaderText("太棒了！");
        _alert.setContentText("您的操作已成功执行！");
        _alert.initOwner(stage);
        _alert.show();
    }

}

