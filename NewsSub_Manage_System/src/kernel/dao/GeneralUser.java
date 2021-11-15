package kernel.dao;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class GeneralUser implements User, Serializable {
    private IntegerProperty id;         // 编号
    private StringProperty userName;    // 用户名
    private StringProperty passWord;    // 密码
    private StringProperty realName;    // 真实姓名
    private StringProperty idCard;      // 身份证号
    private StringProperty phone;       // 联系电话
    private StringProperty address;     // 联系地址
    public GeneralUser() {}

    public GeneralUser(String userName, String passWord) {
        this.userName = new SimpleStringProperty(userName);
        this.passWord = new SimpleStringProperty(passWord);
    }
    public GeneralUser(int id, String userName, String passWord, String realName, String idCard, String phone, String address) {
        this.id = new SimpleIntegerProperty(id);
        this.userName = new SimpleStringProperty(userName);
        this.passWord = new SimpleStringProperty(passWord);
        this.realName = new SimpleStringProperty(realName);
        this.idCard = new SimpleStringProperty(idCard);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
    }

//   User接口的实现
    @Override
    public String showUserName() { return getUserName(); }

    @Override
    public String showPassWord() { return getPassWord(); }

    @Override
    public String showType() { return "普通用户"; }

//   getter and setter
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

    public void setUserName(String userName) { this.userName.set(userName); }

    public String getPassWord() { return passWord.get(); }

    public StringProperty passWordProperty() { return passWord; }

    public void setPassWord(String passWord) { this.passWord.set(passWord); }

    public String getRealName() { return realName.get(); }

    public StringProperty realNameProperty() { return realName; }

    public void setRealName(String realName) { this.realName.set(realName); }

    public String getIdCard() { return idCard.get(); }

    public StringProperty idCardProperty() { return idCard; }

    public void setIdCard(String idCard) { this.idCard.set(idCard); }

    public String getPhone() { return phone.get(); }

    public StringProperty phoneProperty() { return phone; }

    public void setPhone(String phone) { this.phone.set(phone); }

    public String getAddress() { return address.get(); }

    public StringProperty addressProperty() { return address; }

    public void setAddress(String address) { this.address.set(address); }

    @Override
    public String toString() {
        return "GeneralUser{" +
                "id=" + id +
                ", userName=" + userName +
                ", passWord=" + passWord +
                ", realName=" + realName +
                ", idCard=" + idCard +
                ", phone=" + phone +
                ", address=" + address +
                '}';
    }
}
