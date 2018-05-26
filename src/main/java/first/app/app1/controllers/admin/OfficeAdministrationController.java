package first.app.app1.controllers.admin;

import first.app.app1.models.User;
import first.app.app1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OfficeAdministrationController {

    @Autowired
    UserService userService;

    @GetMapping("/admin/office-requests")
    public String showRequests(Model model)
    {
        List<User> users = userService.getRequestsForOffices(0);
        for(int i=0;i<users.size();i++){
            if(users.get(i).getRole().equals("ROLE_ADMIN"))
                users.remove(i);
        }
        model.addAttribute("users",users);
        return "admin/office-requests";
    }

}
