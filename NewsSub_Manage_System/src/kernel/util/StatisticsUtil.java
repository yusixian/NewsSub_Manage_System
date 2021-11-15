package kernel.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import kernel.NowUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 统计工具为项目提供向数据库请求的所有细节，统一向主程序返回Data数据集
 * 大佬tql
 * @author ShiJiahuan
 * @author www.github.com/Haut-Stone
 */
public class StatisticsUtil {
    /**
     * 按名称查询当前用户订单数统计数据
     * @param uid 用户编号
     * @return 用于饼图的数据集
     */
    public static ObservableList<PieChart.Data> makeNameOrderData(int uid) {
        return makeGeneralUserOrderData("select  c.name as mName " +
                "from `order` a, `generalUser` b, `magazine` c " +
                "where a.uid = b.id and a.mid = c.id and b.id = ?;", uid);
    }

    /**
     * 按分类查询当前用户订单数统计数据
     * @param uid 用户名
     * @return 用于饼图的数据集
     */
    public static ObservableList<PieChart.Data> makeClassOrderData(int uid) {
        return makeGeneralUserOrderData("select d.className as mName " +
                "from `order` a, `generalUser` b, `magazine` c, `mClass` d " +
                "where a.uid = b.id and a.mid = c.id and d.id = c.classNumber and b.id = ?;", uid);
    }


    /**
     * 按名称查询当前用户总金额统计数据
     * @param username 用户名
     * @return 用于饼图的数据集
     */
    public static ObservableList<PieChart.Data> makeNamePriceData(String username) {
        return makeGeneralUserPriceData("select c.name as name, a.totalPrice as price " +
                "from `order` a, `generalUser` b, `magazine` c, `mClass` d " +
                "where a.uid = b.id and a.mid = c.id and d.id = c.classNumber and b.userName = ?;", username);
    }

    /**
     * 按分类查询当前用户总金额统计数据
     * @param username 用户名
     * @return 用于饼图的数据集
     */
    public static ObservableList<PieChart.Data> makeClassPriceData(String username) {
        return makeGeneralUserPriceData("select d.className as name, a.totalPrice as price " +
                "from `order` a, `generalUser` b, `magazine` c, `mClass` d " +
                "where a.uid = b.id and a.mid = c.id and d.id = c.classNumber and b.userName = ?;", username);
    }
    /**
     * 查询用户订单数据并返回打包好的数据集
     * @param sql 数据库查询语句
     * @param uid 用户编号
     * @return data 用于饼图的数据集
     */
    private static ObservableList<PieChart.Data> makeGeneralUserOrderData(String sql, int uid) {
        //从数据库查询相关信息
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        List<Object> params = new ArrayList<>();
        params.add(uid);

        Map<String, Integer> nameOrderData = new HashMap<String, Integer>();
        try {
            List<Map<String, Object>> names = dbUtils.findModeResult(sql, params);
            for (Map<String, Object> name : names) {
                String keyName = name.get("mName").toString();
                if (nameOrderData.containsKey(keyName)) {
                    nameOrderData.put(keyName, nameOrderData.get(keyName) + 1);
                } else {
                    nameOrderData.put(keyName, 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(nameOrderData);
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (String s : nameOrderData.keySet()) {
            data.add(new PieChart.Data(s, nameOrderData.get(s)));
        }
        return data;
    }

    /**
     * 查询用户总金额数据并返回打包好的数据集
     * @param sql 数据库查询语句
     * @param username 用户名
     * @return data 用于饼图的数据集
     */
    private static ObservableList<PieChart.Data> makeGeneralUserPriceData(String sql, String username) {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        List<Object> params = new ArrayList<>();
        params.add(username);
        Map<String, Integer> nameOrderData = new HashMap<String, Integer>();

        try {
            List<Map<String, Object>> infos = dbUtils.findModeResult(sql, params);
            for (Map<String, Object> info : infos) {
                String keyName = info.get("name").toString();
                if (nameOrderData.containsKey(keyName)) {// 若已经包括
                    nameOrderData.put(keyName, nameOrderData.get(keyName) + (Integer) info.get("price"));//加到原来的数据上
                } else {
                    nameOrderData.put(keyName, (Integer) info.get("price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(nameOrderData);
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (String s : nameOrderData.keySet()) {
            data.add(new PieChart.Data(s, nameOrderData.get(s)));
        }
        return data;
    }

    /**
     * 最重统计数据通用查询
     * @param sql 数据库查询语句
     */
    public static Map<String, Object> commonMostquery(String sql) {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        List<Object> params = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        // 无需填充参数，直接上sql语句打包成字典返回
        try {
            data = dbUtils.findSimpleResult(sql, params);
        } catch (SQLException e) {
            System.out.println("查询失败:"+data);
            e.printStackTrace();
        }
//        System.out.println(data);
        return data;
    }

    /**
     * 从数据库中semagazinereview视图查询订单总金额最大的杂志
     * @return 以字典形式返回
     */
    public static Map<String, Object> mostPopularMagazine() {
        //由于数据库中视图已经建好，这里直接查询视图中的最值即可
        System.out.println("正在查询最受欢迎报刊");
        Map<String, Object> result = new HashMap<>();
        result = commonMostquery("select * from `semagazinereview` as t where t.totalPrice = (select max(totalPrice) from `semagazinereview` as t1);");
        return result;
    }

    /**
     * 从数据库中seuserreview视图查询订单总金额最大的用户
     * @return 以字典形式返回
     */
    public static Map<String, Object> mostRichUser() {
        System.out.println("正在查询最富有用户");
        Map<String, Object> result = new HashMap<>();
        result = commonMostquery("select * from `seuserreview` as t where t.totalPrice = (select max(totalPrice) from `seuserreview` as t1);");
        System.out.println(result);
        return result;
    }

    /**
     * 对条形图所需要的数据进行获取。
     * @param sql 数据库查询语句
     */
    public static List<Map<String, Object>> commonBarChartQuery(String sql) {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        List<Object> params = new ArrayList<>();
        List<Map<String, Object>> data = new ArrayList<>();
        try {
            data = dbUtils.findModeResult(sql, params);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}

