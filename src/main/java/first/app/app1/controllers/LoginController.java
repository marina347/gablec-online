package first.app.app1.controllers;

import com.auth0.jwt.internal.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Notification;
import com.google.android.gcm.server.Sender;
import first.app.app1.data.out.NotificationDetails;
import first.app.app1.models.Office;
import first.app.app1.models.User;
import first.app.app1.service.NotificationService;
import first.app.app1.service.OfficeService;
import first.app.app1.service.TodayOrdersService;
import first.app.app1.service.UserService;
import first.app.app1.utils.UserSessionUtils;
import nl.martijndwars.webpush.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    OfficeService officeService;

    @Autowired
    TodayOrdersService todayOrdersService;

    @Autowired
    NotificationService notificationService;

    @GetMapping("/login")
    public String showLogin(){
        todayOrdersService.prepareOrders();
        return "login";
    }

    @PostMapping("/login-error")
    public String showLoginError(Model model){
        String message="Unsuccessful login! Try with different username or password!";
        model.addAttribute("message",message);
        return "login";
    }

    @GetMapping("/home")
    public String showHomeOrOffices(Model model){
        User user = userService.findByUsername(UserSessionUtils.getUserUsername());
        if(!user.getRole().equals(User.adminRole) && ((user.getOffice()==null) || (user.getOffice() != null && user.getOdobreno() == 0))){
            List<Office> offices=officeService.getAllOffices();
            model.addAttribute("offices",offices);
            return "office";
        }
        else
         return "redirect:order";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(httpServletRequest,httpServletResponse,authentication);
        }
        return "redirect:/login";
    }

}
