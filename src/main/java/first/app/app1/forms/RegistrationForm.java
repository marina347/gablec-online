package first.app.app1.forms;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationForm {
    @NotNull
    @NotBlank
    @Size(min=2,max=15)
    private String username;
    @NotNull
    @NotBlank
    @Size(min=8,max=16)
    private String password;
    @NotNull
    @NotBlank
    @Size(min=8,max=16)
    private String repeatedPassword;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String surname;

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

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
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
}
