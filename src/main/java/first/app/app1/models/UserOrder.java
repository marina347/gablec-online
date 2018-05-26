package first.app.app1.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class UserOrder {
    @Id
    @GeneratedValue
    private int id;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "userOrder", cascade = {CascadeType.PERSIST})
    private List<OrderItem> orderItemList;

    @ManyToOne(targetEntity = PaymentMethod.class,fetch = FetchType.LAZY)
    @JoinColumn(nullable = true,name="paymentMethod_id",referencedColumnName="id")
    private PaymentMethod paymentMethod;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name="user_id",referencedColumnName="id")
    private User user;

    public UserOrder() {
    }

    public UserOrder(User user, Cart cart) {
        this.user = user;
        this.orderItemList = new ArrayList<>(cart.getFoodList().size());
        for(CartItem cartItem: cart.getFoodList().values())
        {
            OrderItem orderItem = new OrderItem(cartItem.getFood(), this, cartItem.getCount());
            orderItemList.add(orderItem);
        }
        this.orderDate = new Date(Calendar.getInstance().getTime().getTime());
    }



    private Date orderDate;

    private int completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotal()
    {
        double sum = 0;
        for(OrderItem orderItem: orderItemList)
        {
            sum += orderItem.getFood().getPrice()*orderItem.getCount();
        }
        return sum;
    }
}
