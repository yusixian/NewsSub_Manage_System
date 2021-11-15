package kernel.util;

import javafx.stage.FileChooser;
import kernel.Main;

import java.io.*;

/**
 * 文件操作通用函数，用于选择用户文件上传等其他操作
 */
public class ImageUtil {

    /**
     * 保存图片
     * @param image 要进行操作的图片
     */
    public static void save(File image) {
        /*
         *==================================================
         *                 !!important!!
         *        这里的路径一定要写成下面这个样子的路径
         *==================================================
         */
        File imageFileCopy = new File("src/kernel/views/css/images/", image.getName());

        // 创建复制流
        InputStream in = null;
        OutputStream out = null;

        // 复制文件
        try {
            if (!imageFileCopy.exists()) {
                imageFileCopy.createNewFile();      // 若文件不存在则创建新文件
            }
            in = new FileInputStream(image);
            out = new FileOutputStream(imageFileCopy);
            byte[] temp = new byte[2048];
            int length = 0;
            while ((length = in.read(temp)) != -1) {
                out.write(temp, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭文件输入输出流
                in.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 弹出窗口让用户自行选择图片，返回选择的文件
     * @param mainApp 当前主程序
     * @return 选择的文件（图片）
     */
    public static File choose(Main mainApp) {
        FileChooser fileChooser = new FileChooser();
        // 设置过滤的文件类型
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter(
                "JPG files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter(
                "JPEG files (*.jpeg)", "*.jpeg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter(
                "PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(jpgFilter);
        fileChooser.getExtensionFilters().add(jpegFilter);
        fileChooser.getExtensionFilters().add(pngFilter);
        File image = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        return image;
    }

    public static void delete(String imageName) {
        File waitToDeletaImage = new File("src/kernel/statics/images/", imageName);
        waitToDeletaImage.delete();
        System.out.println("封面" + imageName + "已被删除");
    }
}
