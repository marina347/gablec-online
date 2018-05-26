package first.app.app1.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class UserWalletTransaction {
    @Id
    @GeneratedValue
    int id;
    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name="user_from",referencedColumnName="id")
    User userFrom;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name="user_to",referencedColumnName="id")
    User userTo;

    float amount;

    Date date;

    int completed;

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
