package kernel.dao;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.Serializable;

public class Magazine implements Serializable {
    private IntegerProperty id;         // 报刊编号
    private StringProperty coverPath;   // 封面地址
    private StringProperty name;        // 报刊名称
    private StringProperty office;      // 出版报社
    private StringProperty cycle;       // 出版周期
    private StringProperty price;       // 周期报价
    private StringProperty intro;       // 内容简介
    private StringProperty className;   // 分类名

    public Magazine() { }
    public Magazine(int id, String coverPath, String name, String office, String cycle, String price, String intro, String className) {
        this.id = new SimpleIntegerProperty(id);;
        this.coverPath = new SimpleStringProperty(coverPath);
        this.name = new SimpleStringProperty(name);
        this.office = new SimpleStringProperty(office);
        this.cycle = new SimpleStringProperty(cycle);
        this.price= new SimpleStringProperty(price);
        this.intro= new SimpleStringProperty(intro);
        this.className = new SimpleStringProperty(className);
    }


//    getter and setter
    public int getId() { return id.get(); }

    public IntegerProperty idProperty() { return id; }

    public void setId(int id) { this.id.set(id); }

    public String getCoverPath() { return coverPath.get(); }

    public StringProperty coverPathProperty() { return coverPath; }

    public void setCoverPath(String coverPath) { this.coverPath.set(coverPath); }

    public String getName() { return name.get(); }

    public StringProperty nameProperty() { return name; }

    public void setName(String name) { this.name.set(name); }

    public String getOffice() { return office.get(); }

    public StringProperty officeProperty() { return office; }

    public void setOffice(String office) { this.office.set(office); }

    public String getCycle() { return cycle.get(); }

    public StringProperty cycleProperty() { return cycle; }

    public void setCycle(String cycle) { this.cycle.set(cycle); }

    public String getPrice() { return price.get(); }

    public StringProperty priceProperty() { return price; }

    public void setPrice(String price) { this.price.set(price); }

    public String getIntro() { return intro.get(); }

    public StringProperty introProperty() { return intro; }

    public void setIntro(String intro) { this.intro.set(intro); }

    public String getClassName() { return className.get(); }

    public StringProperty classNameProperty() { return className; }

    public void setClassName(String className) { this.className.set(className); }

    @Override
    public String toString() {
        return "Magazine{" +
                "id=" + id +
                ", coverPath=" + coverPath +
                ", name=" + name +
                ", office=" + office +
                ", cycle=" + cycle +
                ", price=" + price +
                ", intro=" + intro +
                ", className=" + className +
                '}';
    }
}
