package first.app.app1.models;

import javax.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(targetEntity = Food.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name="food_id",referencedColumnName="id")
    private Food food;

    @ManyToOne(targetEntity = UserOrder.class,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name="order_id",referencedColumnName="id")
    private UserOrder userOrder;
    private int count;

    public OrderItem() {
    }

    public OrderItem(Food food, UserOrder userOrder, int count) {
        this.food = food;
        this.userOrder = userOrder;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UserOrder getOrder() {
        return userOrder;
    }

    public void setOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
    }
}
