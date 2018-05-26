package first.app.app1.controllers;

import com.ibm.watson.developer_cloud.service.exception.InternalServerErrorException;
import first.app.app1.daos.UserDao;
import first.app.app1.forms.RegistrationForm;
import first.app.app1.models.User;
import first.app.app1.utils.UserSessionUtils;
import first.app.app1.validators.RegistrationValidator;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
@Controller
public class RegistrationController {
    @Autowired
    UserDao userDao;

    @Autowired
    RegistrationValidator registrationValidator;
    @GetMapping("/registration")
    public String showRegistration(Model model){

        model.addAttribute("registrationForm",new RegistrationForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String submitRegistration(@Valid RegistrationForm registrationForm, BindingResult bindingResult, Model model){


        model.addAttribute("registrationForm",registrationForm);
        registrationValidator.validate(registrationForm,bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        else{
            User user=new User();
            user.setPassword((new BCryptPasswordEncoder()).encode(registrationForm.getPassword()));
            user.setUsername(registrationForm.getUsername());
            user.setName(registrationForm.getName());
            user.setSurname(registrationForm.getSurname());
            user.setRole(User.customerRole);
            userDao.save(user);
        }

        return "login";
    }

    @ResponseBody
    @GetMapping("/service-worker.js")
    public String returnServiWorker()
    {
        try
        {

            User user = userDao.findByUsername(UserSessionUtils.getUserUsername());
            return "'use strict';\n" +
                "\n" +
                "self.addEventListener('push', function(event) {\n" +
                "  console.log('Received a push message', event);\n" +
                "    let username = '"+ user.getUsername()+"';\n" +
                "    fetch('/app1/rest/notifications?username='+username)\n" +
                "        .then(\n" +
                "            function(response) {\n" +
                "                if (response.status !== 200) {\n" +
                "                    console.log('Looks like there was a problem. Status Code: ' +\n" +
                "                        response.status);\n" +
                "                    return;\n" +
                "                }\n" +
                "\n" +
                "                // Examine the text in the response\n" +
                "                response.json().then(function(data) {\n" +
                "                    console.log(data);\n" +
                "                    let title = data.title;\n" +
                "                    let body =  data.body;\n" +
                "                    let icon = data.icon;\n" +
                "                    let tag = 'simple-push-demo-notification-tag';\n" +
                "\n" +
                "                    event.waitUntil(\n" +
                "                        self.registration.showNotification(title, {\n" +
                "                            body: body,\n" +
                "                            icon: icon,\n" +
                "                            tag: tag\n" +
                "                        })\n" +
                "                    );\n" +
                "                });\n" +
                "            }\n" +
                "        )\n" +
                "        .catch(function(err) {\n" +
                "            console.log('Fetch Error :-S', err);\n" +
                "        });\n" +
                "\n" +
                "\n" +
                "});\n" +
                "\n" +
                "self.addEventListener('notificationclick', function(event) {\n" +
                "  console.log('On notification click: ', event.notification.tag);\n" +
                "  // Android doesn’t close the notification when you click on it\n" +
                "  // See: http://crbug.com/463146\n" +
                "  event.notification.close();\n" +
                "\n" +
                "  // This looks to see if the current is already open and\n" +
                "  // focuses if it is\n" +
                "  event.waitUntil(clients.matchAll({\n" +
                "    type: 'window'\n" +
                "  }).then(function(clientList) {\n" +
                "    for (var i = 0; i < clientList.length; i++) {\n" +
                "      var client = clientList[i];\n" +
                "      if (client.url === '/' && 'focus' in client) {\n" +
                "        return client.focus();\n" +
                "      }\n" +
                "    }\n" +
                "    if (clients.openWindow) {\n" +
                "      return clients.openWindow('/');\n" +
                "    }\n" +
                "  }));\n" +
                "});";
        }
        catch (Exception e)
        {
            throw new RuntimeException("Sorry m8");
        }
    }


    @ResponseBody
    @GetMapping("/manifest.sample.json")
    public String dajManifest()
    {

        return "{\n" +
                "  \"name\": \"Push Demo\",\n" +
                "  \"short_name\": \"Push Demo\",\n" +
                "  \"icons\": [{\n" +
                "        \"src\": \"images/icon-192x192.png\",\n" +
                "        \"sizes\": \"192x192\"\n" +
                "      }],\n" +
                "  \"start_url\": \"./index.html?homescreen=1\",\n" +
                "  \"display\": \"standalone\",\n" +
                "  \"gcm_sender_id\": \"562552855160\"\n" +
                "}";
    }

}
