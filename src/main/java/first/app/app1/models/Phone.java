package first.app.app1.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Phone {
    @Id
    @GeneratedValue
    private int id;

    private String number;
    private String description;
    @ManyToOne(targetEntity = Restaurant.class,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name="restaurant_id",referencedColumnName="id")
    Restaurant restaurant;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public boolean getStartsWith09()
    {
        return this.number.startsWith("09");
    }
}
