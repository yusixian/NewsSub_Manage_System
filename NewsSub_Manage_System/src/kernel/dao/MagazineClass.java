package kernel.dao;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import javax.crypto.Mac;

public class MagazineClass {
    private IntegerProperty id;         // 分类编号
    private StringProperty className;        // 分类名
    public MagazineClass() {}

//  getter & setter
    public int getId() { return id.get(); }

    public IntegerProperty idProperty() { return id; }

    public void setId(int id) { this.id.set(id); }

    public String getName() { return className.get(); }

    public StringProperty nameProperty() { return className; }

    public void setName(String name) { this.className.set(name); }

    @Override
    public String toString() {
        return "MagazineClass{" +
                "id=" + id +
                ", className=" + className +
                '}';
    }
}
