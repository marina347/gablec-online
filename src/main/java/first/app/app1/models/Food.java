package first.app.app1.models;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.Null;

@Entity
public class Food {
    @Id
    @GeneratedValue
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private float price;


    @ManyToOne(targetEntity = Restaurant.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name="restaurant_id",referencedColumnName="id")
    Restaurant restaurant;

    @ManyToOne(targetEntity = Category.class,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name="category_id",referencedColumnName="id")
    Category category;


    @ManyToOne(targetEntity = DailyMenu.class,fetch = FetchType.LAZY)
    @JoinColumn(nullable = true,name="daily_menu_id",referencedColumnName="id")
    DailyMenu dailyMenu;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public DailyMenu getDailyMenu() {
        return dailyMenu;
    }

    public void setDailyMenu(DailyMenu dailyMenu) {
        this.dailyMenu = dailyMenu;
    }
}
