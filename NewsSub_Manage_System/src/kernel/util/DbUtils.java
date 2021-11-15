package kernel.util;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtils {
    private static String URL = "jdbc:mysql://localhost:3306/project?characterEncoding=utf-8&serverTimezone=UTC";
    private static String USER = "root";
    private static String PASSWORD ="369789";
    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private PreparedStatement ps;
    private Connection connection;  //数据库连接对象
    private ResultSet resultSet;    //查询结果集
    public DbUtils() {
        try {
            Class.forName(DRIVER);
            System.out.println("数据库连接成功");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return connection 数据库连接对象
     */
    public Connection getConnection(){
        try {
            // 获取数据库连接
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 数据库中数据的更新，即增加、删除和修改操作
     * @param sql 数据库的查询语句
     * @param params 待填充的参数列表
     * @return 是否更新成功
     */
    public boolean upadteByPreState(String sql, List<Object> params) {
        try {
            int rowNum = -1;
            ps = connection.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for(int i = 0; i < params.size();  ++i) {
                    ps.setObject(i+1, params.get(i));
                }
            }
            rowNum = ps.executeUpdate();
            if (rowNum == -1) return false;
            else return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据更新失败！");
            return false;
        }
    }

    /**
     * 数据库数据的增加，删除，修改
     * @param sql 待填充参数的数据库查询语句
     * @param params 准备填充的参数列表
     * @throws SQLException 数据库更新异常
     * @return 是否更新成功
     */
    public boolean updateByPreparedStatement(String sql, List<Object> params) throws SQLException {
        int rowNum = -1;        // 如果行号小于零说明更新失败
        ps = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for(int i = 0; i < params.size();  ++i) {
                ps.setObject(i+1, params.get(i));
            }
        }
        rowNum = ps.executeUpdate(); //executeUpdate的返回值是一个整数，指示受影响的行数（即更新计数）
        return rowNum > 0;
    }

    /**
     * 查询单条数据库数据，并打包成字典的形式返回
     * @param sql 数据库的查询语句
     * @param params 待填充的参数列表
     * @throws SQLException 抛出数据库操作异常
     * @return 单个数据库信息的字典
     */
    public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException {
        Map<String, Object> mp = new HashMap<>();
        ps = connection.prepareStatement(sql);
//        System.out.println(ps);
        if (params != null && !params.isEmpty()) {
            for(int i = 0; i < params.size();  ++i) {
                ps.setObject(i+1, params.get(i));
            }
        }
        resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();   // 获取元数据
        int col = metaData.getColumnCount();
        while (resultSet.next()) {
            for(int i = 0; i < col; ++i) {
                String colName = metaData.getColumnLabel(i+1);
                Object colvalue = resultSet.getObject(colName);
                if(colvalue == null) {
                    colvalue = "";
                }
                mp.put(colName, colvalue);
            }
        }
        return mp;
    }
    /**
     * 查询多条数据库数据，打包成字典，并放入列表中返回
     * @param sql 待填充参数的数据库查询语句
     * @param params 准备填充的参数列表
     * @throws SQLException 抛出数据库操作异常
     * @return 装有多组数据map的list
     */
    public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException {
        List<Map<String, Object>> mps = new ArrayList<>();
        ps = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i+1, params.get(i));
            }
        }
        resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < col; i++) {
                String colName = metaData.getColumnLabel(i+1);
                Object colValue = resultSet.getObject(colName);
                if (colValue == null) {
                    colValue = "";
                }
                map.put(colName, colValue);
            }
            mps.add(map);
        }
        return mps;
    }

    /**
     * 通过反射机制查询单条数据库数据，处理完绑定后打包，并直接返回包装好的对象
     * @param <T> 当前数据类型
     * @param sql 待填充参数的数据库查询语句
     * @param params 待填充的参数列表
     * @param cls 要打包的对象的类型
     * @throws Exception 抛出一个异常
     * @return 直接返回包装好的数据
     */
    public <T> T findSimpleProResult(String sql, List<Object> params, Class<T> cls) throws Exception {
        T resultObject = null;
        ps = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i+1, params.get(i));
            }
        }
        resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
//        System.out.println(ps); // 调试用
        int col = metaData.getColumnCount();
        while (resultSet.next()) {

            resultObject = cls.newInstance(); // 创建一个该类的一个新实例

            // 对每项数据依次处理
            for (int i = 0; i < col; i++) {
                String colName = metaData.getColumnName(i+1);
                Object colValue = resultSet.getObject(colName);
//                System.out.println(colName);
//                System.out.println(colValue);
                Object newValue = null;
                if (colValue == null) {
                    newValue = "";
                } else if (colValue.getClass() == Integer.class) {
                    newValue = new SimpleIntegerProperty(Integer.parseInt(colValue.toString()));
                } else if (colValue.getClass() == String.class) {
                    newValue = new SimpleStringProperty(colValue.toString());
                }
                Field field = cls.getDeclaredField(colName);
                field.setAccessible(true);
                field.set(resultObject, newValue);
            }
        }
        return resultObject;
    }

    /**
     * 通过反射机制查询多条数据库数据，处理双向绑定并将对象封装好后填如list中返回
     * @param <T> 当前数据类型
     * @param sql 数据库查询语句
     * @param params 数据库查询参数
     * @param cls 要打包的对象的类型
     * @throws Exception 抛出一个异常
     * @return 装有cls相应对象的列表
     */
    public <T> List<T> findMoreProResult(String sql, List<Object> params, Class<T> cls) throws Exception {
        List<T> list = new ArrayList<>();
        ps = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i+1, params.get(i));
            }
        }
//        System.out.println(ps);
        resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
//        System.out.println(metaData);
        int col = metaData.getColumnCount();
        while (resultSet.next()) {
            T resultObject = cls.newInstance();
            for (int i = 0; i < col; i++) {
                String colName = metaData.getColumnLabel(i+1);
                Object colValue = resultSet.getObject(colName);
//                System.out.println(colName);
                Object newValue = null;
                if (colValue == null) {
                    colValue = "";
                } else if (colValue.getClass() == Integer.class) {
                    newValue = new SimpleIntegerProperty(Integer.parseInt(colValue.toString()));
                } else if (colValue.getClass() == String.class) {
                    newValue = new SimpleStringProperty(colValue.toString());
                }
                Field field = cls.getDeclaredField(colName);
                field.setAccessible(true);
                field.set(resultObject, newValue);
            }
            list.add(resultObject);
        }
        return list;
    }

    /**
     * 通过反射机制查询单条数据库数据，并直接返回包装好的对象
     * @param <T> 当前数据类型
     * @param sql 待填充参数的数据库查询语句
     * @param params 带填充的参数列表
     * @param cls 要打包的对象的类型
     * @throws Exception 返回一个异常
     * @return 直接返回包装好的数据
     */
    public <T> T findSimpleRefResult(String sql, List<Object> params, Class<T> cls) throws Exception {
        T resultObject = null;
        ps = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i+1, params.get(i));
            }
        }
        resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col = metaData.getColumnCount();
        while (resultSet.next()) {
            resultObject = cls.newInstance(); // 创建一个该类的一个新实例
            for (int i = 0; i < col; i++) {
                String colName = metaData.getColumnName(i+1);
                Object colValue = resultSet.getObject(colName);
                if (colValue == null) {
                    colValue = "";
                }
                Field field = cls.getDeclaredField(colName);
                field.setAccessible(true);
                field.set(resultObject, colValue);
            }
        }
        return resultObject;
    }

    /**
     * 通过反射机制查询多条数据库数据，将对象封装好后填入list中，并返回
     * @param <T> 当前数据类型
     * @param sql 数据库查询语句
     * @param params 数据库查询参数
     * @param cls 要打包的对象的类型
     * @throws Exception 返回一个异常
     * @return 装有cls相应对象的列表
     */
    public <T> List<T> findMoreRefResult(String sql, List<Object> params, Class<T> cls) throws Exception {
        List<T> list = new ArrayList<>();
        ps = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i+1, params.get(i));
            }
        }
        resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col = metaData.getColumnCount();
        while (resultSet.next()) {
            T resultObject = cls.newInstance();
            for (int i = 0; i < col; i++) {
                String colName = metaData.getColumnName(i+1);
                Object colValue = resultSet.getObject(colName);
                if (colValue == null) {
                    colValue = "";
                }
                Field field = cls.getDeclaredField(colName);
                field.setAccessible(true);
                field.set(resultObject, colValue);
            }
            list.add(resultObject);
        }
        return list;
    }

    /**
     * 释放数据库连接
     */
    public void releaseConnection() {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("释放连接失败！");
            }
        }
    }
}
