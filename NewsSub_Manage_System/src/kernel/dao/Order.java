package kernel.dao;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Order {
    private IntegerProperty id;         // 订单编号
    private IntegerProperty uid;        // 用户编号
    private StringProperty userName;    // 用户名
    private IntegerProperty mid;        // 报刊编号
    private StringProperty magazineName;// 报刊名称
    private IntegerProperty cycleNum;   // 订阅周期数
    private IntegerProperty copiesNum;  // 订单份数
    private IntegerProperty totalPrice;   // 总金额

    public Order() {}

//    getter and setter
    public int getId() { return id.get(); }

    public IntegerProperty idProperty() { return id; }

    public void setId(int id) { this.id.set(id); }

    public int getUid() { return uid.get(); }

    public IntegerProperty uidProperty() { return uid; }

    public void setUid(int uid) { this.uid.set(uid); }

    public String getUserName() { return userName.get(); }

    public StringProperty userNameProperty() { return userName; }

    public void setUserName(String userName) { this.userName.set(userName); }

    public int getMid() { return mid.get(); }

    public IntegerProperty midProperty() { return mid; }

    public void setMid(int mid) { this.mid.set(mid); }

    public String getMagazineName() { return magazineName.get(); }

    public StringProperty magazineNameProperty() { return magazineName; }

    public void setMagazineName(String magazineName) { this.magazineName.set(magazineName); }

    public int getCycleNum() { return cycleNum.get(); }

    public IntegerProperty cycleNumProperty() { return cycleNum; }

    public void setCycleNum(int cycleNum) { this.cycleNum.set(cycleNum); }

    public int getCopiesNum() { return copiesNum.get(); }

    public IntegerProperty copiesNumProperty() { return copiesNum; }

    public void setCopiesNum(int copiesNum) { this.copiesNum.set(copiesNum); }

    public int getTotalPrice() { return totalPrice.get(); }

    public IntegerProperty totalPriceProperty() { return totalPrice; }

    public void setTotalPrice(int totalPrice) { this.totalPrice.set(totalPrice); }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", uid=" + uid +
                ", userName=" + userName +
                ", mid=" + mid +
                ", magazineName=" + magazineName +
                ", cycleNum=" + cycleNum +
                ", copiesNum=" + copiesNum +
                ", totalNum=" + totalPrice +
                '}';
    }
}
