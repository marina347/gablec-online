package first.app.app1.models;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String address;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "restaurant")
    List<Phone> phoneList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "restaurant")
    List<Food> foodList;

    @ManyToOne(targetEntity = Office.class,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name="office_id",referencedColumnName="id")
    Office office;

    private Time orderTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Time getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Time orderTime) {
        this.orderTime = orderTime;
    }
}
