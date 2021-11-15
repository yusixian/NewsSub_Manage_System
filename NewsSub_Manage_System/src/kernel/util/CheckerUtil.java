package kernel.util;

import kernel.dao.Admin;
import kernel.dao.GeneralUser;
import kernel.dao.Magazine;
import kernel.dao.Order;
import kernel.util.DbUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckerUtil {
    /**
     * 检查普通用户基本信息是否过长或过短（不包括id）
     * @param un 用户名
     * @param pw 密码
     * @param rname 真实姓名
     * @param idc 身份证号
     * @param pho 联系电话
     * @param addr 联系地址
     * @return errorMessage 错误提示语句
     */
    public static String generalUserInfoCheck(String un, String pw, String rname, String idc, String pho, String addr) {
        String errorMessage = null;
        //检查地址
        if (addr.length() > 60) {
            errorMessage = "地址长度过长";
        }

        // 检查电话
        if (pho.length() > 60) {
            errorMessage = "电话号码过长";
        }

        //检查身份证号
        // TODO: 改成用正则匹配身份证号
        if (idc.length() > 60) {
            errorMessage = "身份证号过长";
        }

        //检查真实姓名
        if (rname.length() > 60) {
            errorMessage = "真实姓名过长？这不是真实姓名吧";
        }

        //用户名是不是符合要求
        if (un.length() == 0) {
            errorMessage = "用户名为空";
        } else if (un.length() > 60) {
            errorMessage = "用户名过长";
        }

        //密码是不是符合要求
        if (pw.length() == 0) {
            errorMessage = "密码为空";
        } else if (pw.length() > 60) {
            errorMessage = "密码过长";
        }

        return errorMessage;
    }

    /**
     * 管理员用户界面接口：检查普通用户更新信息，看id与用户名是否被改动
     * @param oldInfo 旧的普通用户信息
     * @param newInfo 新的普通用户信息
     * @return errorMessage 错误提示语句
     */
    public static String generalUserUpdateCheck(GeneralUser oldInfo, GeneralUser newInfo) {
        // 先检查长度
        String errorMessage = generalUserInfoCheck(newInfo.getUserName(), newInfo.getPassWord(),
                newInfo.getRealName(), newInfo.getIdCard(), newInfo.getPhone(), newInfo.getAddress());

        // 若改动了id和用户名这两个不能重复的信息
        if (oldInfo.getId() != newInfo.getId()) {
            errorMessage = "您不能修改用户的ID";
        }
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        if (!oldInfo.getUserName().equals(newInfo.getUserName())) { // 若改动了用户名 检查是否重复
            try {
                String sql = "select * from `generalUser` where userName = ?";
                List<Object> params = new ArrayList<>();
                params.add(newInfo.getUserName());
                GeneralUser sameUser = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
                if (sameUser != null) {
                    errorMessage = "该用户名已被占用";
                }
            } catch (Exception e) {
                System.out.println("更新用户信息失败！");
                e.printStackTrace();
            }
        }
        return errorMessage;
    }

    /**
     * 普通用户更改个人信息界面接口：将新信息传入检查合法性并检查是否改动用户名及密码，若改动了则检查新的用户名是否已被占用
     * @param olduname 旧用户名
     * @param uname 新用户名
     * @param pw 新密码
     * @param rname 新真实姓名
     * @param idc 新用户身份证号
     * @param pho 新用户联系电话
     * @param addr 新用户地址
     * @return errorMessage 错误提示语句
     */
    public static String userUpdateCheck(String olduname,String uname, String pw, String rname, String idc, String pho, String addr) {
        String errorMessage = generalUserInfoCheck(uname, pw, rname, idc, pho, addr); //检查合法性
        //如果改动了用户名这个不能重复的信息
        if (!olduname.equals(uname)) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "select * from `generalUser` where userName = ?";
            List<Object> params = new ArrayList<>();
            params.add(uname);
            try {
                GeneralUser same = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
                if (same != null) {
                    errorMessage = "该用户名已被占用";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errorMessage;
    }

    /**
     * 检查管理员用户更新信息，看id与用户名是否被合法（用户名是否被占用，id不可修改）
     * @param oldInfo 旧管理员信息
     * @param newInfo 新管理员信息
     * @return errorMessage 错误提示语句
     */
    public static String adminInfoUpdateCheck(Admin oldInfo, Admin newInfo) {
        String errorMessage = null;

        if (newInfo.getUserName() == null || newInfo.getUserName().length() == 0) {
            return errorMessage = "用户名为空";
        }

        if (newInfo.getPassWord() == null || newInfo.getPassWord().length() == 0) {
            return errorMessage = "密码为空";
        }

        // 若改动了id和用户名这两个不能重复的信息
        if (oldInfo.getId() != newInfo.getId()) {
            errorMessage = "您不能修改用户的ID";
        }

        if (!oldInfo.getUserName().equals(newInfo.getUserName())) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "select * from `admin` where userName = ?";
            List<Object> params = new ArrayList<>();
            params.add(newInfo.getUserName());
            try {
                Admin same = dbUtils.findSimpleProResult(sql, params, Admin.class);
                if (same != null) {
                    errorMessage = "该用户名已被占用";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errorMessage;
    }

    /**
     * 用户管理界面检查：管理员注册合法性检查，检查合法性以及id、用户名是否重复
     * @param id 编号
     * @param uname 用户名
     * @param pass 密码
     * @return errorMessage 错误提示语句
     */
    public static String adminSignUpCheck(String id, String uname, String pass) {
        String errorMessage = null;
        // 检查id合法性
        if (id == null || id.length() == 0) {
            return errorMessage = "id为空";
        } else if(id.length() > 11) {
            return  errorMessage = "您输入的id过长";
        } else {
            try {
                Integer.parseInt(id);
            } catch (Exception e) {
                return errorMessage = "id应只含有数字";
            }
        }
        // 检查用户名合法性
        if (uname == null || uname.length() == 0) {
            return errorMessage = "用户名为空";
        }

        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `admin` where id = ?";
        List<Object> params = new ArrayList<>();
        params.add(Integer.parseInt(id));
        try {
            Admin sameUser = dbUtils.findSimpleProResult(sql, params, Admin.class);
            if (sameUser != null) {
                errorMessage = "该Id已被占用";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        sql = "select * from `admin` where userName = ?";
        params.clear();
        params.add(uname);

        try {
            Admin sameUser = dbUtils.findSimpleProResult(sql, params, Admin.class);
            if (sameUser != null) {
                errorMessage = "该用户名已被占用";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pass.length() > 60) {
            errorMessage = "密码过长";
        } else if (pass.length() == 0) {
            errorMessage = "密码为空，请输入密码";
        }

        return errorMessage;
    }
    /**
     * 管理员注册普通用户，检查输入信息
     * @param id id
     * @param un 用户名
     * @param pw 密码
     * @param rname 真实姓名
     * @param idc 身份证号
     * @param pho 联系电话
     * @param addr 联系地址
     * @return errorMessage 错误提示语句
     */
    public static String adminAddUserCheck(String id, String un, String pw, String rname, String idc, String pho, String addr) {
        String errorMessage = null;
        if (id == null || id.length() == 0) {
            return errorMessage = "id为空";
        } else if(id.length() > 11) {
            return  errorMessage = "您输入的id过长";
        } else {
            try {
                Integer.parseInt(id);
            } catch (Exception e) {
                return errorMessage = "id应只含有数字";
            }
        }

        // 检查id是否已经被注册
        DbUtils dbUtils= new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `generalUser` where id = ?";
        List<Object> params = new ArrayList<>();
        params.add(Integer.parseInt(id));
        try {
            GeneralUser sameUser = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
            if (sameUser != null) {
                errorMessage = "该id已被注册";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 检查该用户是否已被注册
        sql = "select * from `generalUser` where userName = ?";
        params.clear();
        params.add(un);
        try {
            GeneralUser sameUser = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
            if (sameUser != null) {
                errorMessage = "该用户名已被注册";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMessage;
    }
     /**
     * 注册界面检查：普通用户注册信息，检查合法性
     * @param un 用户名
     * @param pw 密码
     * @param rname 真实姓名
     * @param idc 身份证号
     * @param pho 联系电话
     * @param addr 联系地址
     * @return errorMessage 错误提示语句
     */
    public static String generalUserSignUpCheck(String un, String pw, String rname, String idc, String pho, String addr) {
        String errorMessage = generalUserInfoCheck(un, pw, rname, idc, pho, addr); // 检查基本信息合法性
        // 检查该用户是否已被注册
        DbUtils dbUtils= new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `generalUser` where userName = ?";
        List<Object> params = new ArrayList<>();
        params.add(un);
        try {
            GeneralUser sameUser = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
            if (sameUser != null) {
                errorMessage = "该用户名已被注册";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMessage;
    }

    /**
     * 用户管理注册界面检查：检查合法性 并在原来的基础上检查id是否被注册
     * @param id 用户编号
     * @param un 用户名
     * @param pw 密码
     * @param rname 真实姓名
     * @param idc 身份证号
     * @param pho 联系电话
     * @param addr 联系地址
     * @return errorMessage 错误提示语句
     */
    public static String generalUserManageSignUpCheck(String id, String un, String pw, String rname, String idc, String pho, String addr) {
        String errorMessage = generalUserSignUpCheck(un, pw, rname, idc, pho, addr); // 检查注册信息是否合法

        // 在原来的基础上在检查id是否已经被注册
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `generalUser` where id = ?";
        List<Object> params = new ArrayList<>();

        if (id == null || id.length() == 0) {
            return errorMessage = "id为空";
        } else if (id.length() > 11) {
            return errorMessage = "您输入的id过长";
        } else {
            try {
                Integer.parseInt(id);
            } catch (Exception e) {
                return errorMessage = "id应只含有数字";
            }
        }

        params.add(Integer.parseInt(id));

        try {
            GeneralUser sameUser = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
            if (sameUser != null) {
                errorMessage = "该Id已被注册";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMessage;
    }
    /**
     * 检查新加报刊分类信息，看是否合法
     * @param id id
     * @param className 报刊名称
     * @return errorMessage 错误提示语句
     */
    public static String magazineClassSignUpCheck(String id, String className) {
        String errorMessage = null;
        // 检查ID;
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        if (id == null || id.length() == 0) {
            return errorMessage = "id为空";
        } else if(id.length() > 11) {
            return  errorMessage = "您输入的id过长";
        } else {
            try {
                Integer.parseInt(id);
            } catch (Exception e) {
                return  errorMessage = "id应包含有数字";
            }
            String sql = "select * from `mClass` where id = ?";
            List<Object> params = new ArrayList<>();
            params.add(Integer.parseInt(id));
            try {
                Map<String, Object> sameMagazine = dbUtils.findSimpleResult(sql, params);
                if (!sameMagazine.isEmpty()) {
                    return errorMessage = "该ID已被注册";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //检查分类名
        if (className == null || className.length() == 0) {
            errorMessage = "分类名为空";
        } else {
            String sql = "select * from `mClass` where className = ?";
            List<Object> params = new ArrayList<>();
            params.add(className);
            try {
                Map<String, Object> same = dbUtils.findSimpleResult(sql, params);
                if (!same.isEmpty()) {
                    errorMessage = "该分类名已被使用";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return errorMessage;
    }


    /**
     * 检查报刊杂志的常规信息
     * @param id 报刊编号
     * @param mname 报刊名称
     * @param offic 出版报社
     * @param cycle 出版周期
     * @param price 周期报价
     * @param mclas 报刊分类
     * @param intor 内容简介
     * @return
     */
    public static String magazineRegularCheck(String id, String mname, String offic, String cycle, String price, String mclas, String intor) {
        String errorMessage = null;
        System.out.println("进入杂志检查");
        // 检查常规项
        if (offic.length() == 0) {
            errorMessage = "出版社为空";
        }
        if (cycle.length() == 0) {
            errorMessage = "出版周期为空";
        }
        if (price.length() == 0) {
            errorMessage = "价格为空";
        }
        if (mclas.length() == 0) {
            errorMessage = "没有选择分类";
        }
        if (intor.length() > 140) {
            errorMessage = "介绍过长";
        }
        if (id.length() > 11) {
            errorMessage = "id过长";
        }
        if (mname.length() > 60) {
            errorMessage = "用户名过长";
        }
        return errorMessage;
    }

    /**
     * 检查新加报刊信息，检查常规信息是否合法，并检查ID, 图片, 杂志名重复.
     * @param id 报刊编号
     * @param image 报刊封面
     * @param mname 报刊名称
     * @param offic 出版社
     * @param cycle 周期数
     * @param price 周期报价
     * @param mclass 报刊分类
     * @param intro 简介
     * @return errorMessage 错误提示语句
     */
    public static String magazineSignUpCheck(String id, File image, String mname, String offic, String cycle, String price, String mclass, String intro) {
        String errorMessage = magazineRegularCheck(id, mname,offic,cycle,price,mclass,intro);   // 先检查常规信息
        //检查ID, 图片, 杂志名重复.
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select id, coverPath, name from `magazine`";
        List<Object> params = new ArrayList<>();
        try {
            List<Map<String, Object>> infos = dbUtils.findModeResult(sql, params);
            for (Map<String, Object> info : infos) {
//                System.out.println(image.getName() + "  " + info.get("coverPath"));
                if (image == null || image.getName().equals(info.get("coverPath"))) {
                    errorMessage = "该图片已被使用，请换一张";
                }
                if (mname.equals(info.get("name"))) {
                    errorMessage = "该杂志名已被使用";
                }
                if (id == null || id.length() == 0) {
                    return errorMessage = "id为空";
                } else {
                    try {
                        Integer.parseInt(id);
                    } catch (Exception e) {
                        return errorMessage = "id应只含有数字";
                    }
                }
                if (Integer.parseInt(id) == (Integer) info.get("id")) {
                    errorMessage = "该id已被注册";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return errorMessage;
    }

    /**
     * 检查更新报刊信息，检查常规信息是否合法，并且id不允许被修改、报刊名称不可重复
     * @param oldInfo 旧报刊信息
     * @param newInfo 新报刊信息
     * @return errorMessage 错误提示语句
     */
    public static String magazineUpdateCheck(Magazine oldInfo, Magazine newInfo) {
        String errorMessage = magazineRegularCheck(Integer.toString(newInfo.getId()), newInfo.getName(), newInfo.getOffice(), newInfo.getCycle(), newInfo.getPrice(), newInfo.getClassName(), newInfo.getIntro());
        // id不允许被修改
        if (oldInfo.getId() != newInfo.getId()) {
            errorMessage = "您不能修改杂志的ID";
        }

        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        List<Object> params = new ArrayList<>();

        // 检查改后的报刊名称
        if (!oldInfo.getName().equals(newInfo.getName())) {
            String sql = "select * from `magazine` where name = ?";
            params.add(newInfo.getName());
            try {
                Map<String, Object> same = dbUtils.findSimpleResult(sql, params);
                /*
                 *==================================================
                 *                 !!important!!
                 *     字典要判断空不空，而不是null
                 *==================================================
                 */
                if (!same.isEmpty()) {
                    errorMessage = "该报刊名称已被占用，请重新输入报刊名称！";
                }
            } catch (Exception e) {
                System.out.println("检查报刊名称异常");
                e.printStackTrace();
            }
        }

        // 检查改后的图片是否重复
        if (!oldInfo.getCoverPath().equals(newInfo.getCoverPath())) {
            String sql = "select coverPath from `magazine`";
            params.clear();
            try {
                List<Map<String, Object>> infos = dbUtils.findModeResult(sql, params);
                for (Map<String, Object> info : infos) {
                    if (newInfo.getCoverPath().equals(info.get("coverPath"))) {
                        errorMessage = "该封面图片已被使用，请换一张";
                    }
                }
            } catch (SQLException e) {
                System.out.println("检查封面图异常");
                e.printStackTrace();
            }
        }
        return errorMessage;
    }

    /**
     * 检查新加订单信息，检查id、用户名、报刊名、总金额
     * @param id 新订单的id
     * @param userName 新订单的用户名
     * @param magazineName 新订单的报刊名
     * @param cycleNum 新订单的订阅周期数
     * @param copiesNum 新订单的订阅份数
     * @param totalPrice 新订单的总金额
     * @return errorMessage 错误提示语句
     */
    public static String orderSignUpCheck(String id, String userName, String magazineName, String cycleNum, String copiesNum, String totalPrice) {
        String errorMessage = null;

        // 检查ID
        if (id.length() == 0) {
            return errorMessage = "订单编号不能为空";
        } else if(id.length() > 11) {
            return  errorMessage = "您输入的订单编号过长";
        } else {
            try {
                Integer.parseInt(id);
            } catch (Exception e) {
                return errorMessage = "订单编号应只含有数字";
            }

            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "select * from `order` where id = ?";
            List<Object> params = new ArrayList<>();
            params.add(Integer.parseInt(id));
            try {
                Map<String, Object> same = dbUtils.findSimpleResult(sql, params);
                if (!same.isEmpty()) {
                    return errorMessage = "该订单编号已存在";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 检查用户名
        if (userName == null || userName.length() == 0) {
            return errorMessage = "用户名为空";
        } else {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "select * from `generalUser` where userName = ?";
            List<Object> params = new ArrayList<>();
            params.add(userName);
            try {
                Map<String, Object> same = dbUtils.findSimpleResult(sql, params);
                if (same.isEmpty()) {
                    return errorMessage = "该用户不存在，请重新输入用户名";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 检查报刊名
        if (magazineName == null || magazineName.length() == 0) {
            return errorMessage = "报刊名为空！";
        } else {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "select * from `magazine` where `name` = ?";
            List<Object> params = new ArrayList<>();
            params.add(magazineName);
            System.out.println(params);
            try {
                Map<String, Object> same = dbUtils.findSimpleResult(sql, params);
                if (same.isEmpty()) {
                    return errorMessage = "该报刊不存在，请重新输入报刊名";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // TODO:检查月份

        // TODO:检查份数

        // 检查总金额
        if (totalPrice == null || totalPrice.length() == 0) {
            errorMessage = "订单总金额为空";
        } else if(totalPrice.length() > 11) {
            return  errorMessage = "总金额过大";
        } else {
            try {
                Integer.parseInt(totalPrice);
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "订单总金额只能包含数字";
            }
        }

        return errorMessage;

    }

    /**
     * 检查订单更新信息，检查id、用户名、报刊名、总金额
     * @param old 旧订单
     * @param id 新订单的id
     * @param userName 新订单的用户名
     * @param magazineName 新订单的报刊名
     * @param cycle 新订单的订阅周期数
     * @param copiesNum 新订单的订阅份数
     * @param totalPrice 新订单的总金额
     * @return errorMessage 错误提示语句
     */
    public static String orderUpdateCheck(Order old, String id, String userName, String magazineName, String cycle, String copiesNum, String totalPrice) {
        String errorMessage = null;

        // 检查id
        if (id == null || id.length() == 0){
            return errorMessage = "id为空";
        } else if(id.length() > 11) {
            return  errorMessage = "您输入的id过长";
        } else {
            try {
                Integer.parseInt(id);
            } catch (Exception e) {
                e.printStackTrace();
                return errorMessage = "id只能包含数字";
            }
            if (old.getId() != Integer.parseInt(id)) {
                return errorMessage = "您不可以更改订单的ID,想要修改id可以考虑删除后新建订单";
            }
        }

        // 检查用户名
        if (userName == null || userName.length() == 0) {
            return errorMessage = "用户名为空";
        } else {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "select * from `generalUser` where userName = ?";
            List<Object> params = new ArrayList<>();
            params.add(userName);
            try {
                Map<String, Object> same = dbUtils.findSimpleResult(sql, params);
                if (same.isEmpty()) {
                    return  errorMessage = "该用户不存在，请重新输入用户名";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 检查报刊名
        if (magazineName == null || magazineName.length() == 0) {
            return errorMessage = "报刊名为空";
        } else {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();

            String sql = "select * from `magazine` where name = ?";
            List<Object> params = new ArrayList<>();
            params.add(magazineName);
            try {
                Map<String, Object> same = dbUtils.findSimpleResult(sql, params);
                if (same.isEmpty()) {
                    return errorMessage = "该报刊不存在";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 检查订单总金额
        if (totalPrice == null || totalPrice.length() == 0) {
            errorMessage = "订单总金额为空";
        } else if(totalPrice.length() > 11) {
            return  errorMessage = "您输入的金额过大";
        } else {
            try {
                Integer.parseInt(totalPrice);
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "订单金额只能包含数字";
            }
        }
        return errorMessage;
    }

}