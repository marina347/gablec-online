package first.app.app1.watson;

public class UserOrderDiscoveryModel {
    int userId;
    int foodId;
    int count;
    int dayInWeek;

    public UserOrderDiscoveryModel() {
    }

    public UserOrderDiscoveryModel(int userId, int foodId, int count, int dayInWeek) {
        this.userId = userId;
        this.foodId = foodId;
        this.count = count;
        this.dayInWeek = dayInWeek;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDayInWeek() {
        return dayInWeek;
    }

    public void setDayInWeek(int dayInWeek) {
        this.dayInWeek = dayInWeek;
    }
}
