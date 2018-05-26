package first.app.app1.validators;

import first.app.app1.daos.UserDao;
import first.app.app1.forms.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {

    @Autowired
    UserDao userDao;
    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationForm registrationForm = (RegistrationForm)o;
        String username=registrationForm.getUsername();
        String password=registrationForm.getPassword();
        String repeatedPassword=registrationForm.getRepeatedPassword();

        if(!repeatedPassword.equals(password)){
            errors.rejectValue("repeatedPassword","password.no.match");
        }
        if(userDao.findByUsername(username)!=null){
            errors.rejectValue("username","username.exists");
        }
    }


}

