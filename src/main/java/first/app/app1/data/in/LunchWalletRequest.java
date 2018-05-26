package first.app.app1.data.in;

public class LunchWalletRequest {
    int userId;
    float lunchWallet;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getLunchWallet() {
        return lunchWallet;
    }

    public void setLunchWallet(float lunchWallet) {
        this.lunchWallet = lunchWallet;
    }
}
