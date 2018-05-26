package first.app.app1.models;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {
    public static final String customerRole="ROLE_CUSTOMER";
    public static final String adminRole="ROLE_ADMIN";
    @Id
    @GeneratedValue
    private int id;

    private String username;
    private String password;
    private String name;
    private String surname;
    private String role;
    private int odobreno;
    private float lunchWallet;
    private String google_id;

    @ManyToOne(targetEntity = Office.class,fetch = FetchType.LAZY)
    @JoinColumn(nullable = true,name="office_id",referencedColumnName="id")
    private Office office;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static String getCustomerRole() {
        return customerRole;
    }

    public static String getAdminRole() {
        return adminRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public int getOdobreno() {
        return odobreno;
    }

    public void setOdobreno(int odobreno) {
        this.odobreno = odobreno;
    }

    public float getLunchWallet() {
        return lunchWallet;
    }

    public void setLunchWallet(float lunchWallet) {
        this.lunchWallet = lunchWallet;
    }

    public String getGoogleId() {
        return google_id;
    }

    public void setGoogleId(String google_id) {
        this.google_id = google_id;
    }
}
