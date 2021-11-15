package kernel.dao;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.Serializable;

public class Admin implements User, Serializable {
    private IntegerProperty id;         // 编号
    private StringProperty userName;    // 用户名
    private StringProperty passWord;    // 密码
    public Admin() {}
    public Admin(int id, String userName, String passWord) {
        this.id = new SimpleIntegerProperty(id);
        this.passWord = new SimpleStringProperty(passWord);
        this.userName = new SimpleStringProperty(userName);
    }

//      User接口的实现
    @Override
    public String showUserName() { return getUserName(); }
    @Override
    public String showPassWord() { return getPassWord(); }
    @Override
    public String showType() { return "管理员"; }


//    getter and setter
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getUserName() { return userName.get(); }

    public StringProperty userNameProperty() { return userName; }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassWord() { return passWord.get(); }

    public StringProperty passWordProperty() { return passWord; }

    public void setPassWord(String passWord) { this.passWord.set(passWord); }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", userName=" + userName +
                ", passWord=" + passWord +
                '}';
    }
}
