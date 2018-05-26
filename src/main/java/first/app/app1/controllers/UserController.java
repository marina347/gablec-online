package first.app.app1.controllers;

import first.app.app1.models.Office;
import first.app.app1.models.User;
import first.app.app1.models.UserWalletTransaction;
import first.app.app1.service.UserService;
import first.app.app1.service.UserWalletTransactionService;
import first.app.app1.utils.UserSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserWalletTransactionService userWalletTransactionService;

    @GetMapping("/admin/lunch-wallets")
    public String showUsers(Model model){
        User user= userService.findByUsername(UserSessionUtils.getUserUsername());
        List<User> users=userService.getUsersByOfficeId(user.getOffice().getId());
        model.addAttribute("users",users);
        return "admin/lunch-wallets";
    }
    @GetMapping("/borrow-money")
    public String showUsersForTransactions(Model model){
        User user= userService.findByUsername(UserSessionUtils.getUserUsername());
        List<User> users=userService.getUsersByOfficeId(user.getOffice().getId());
        for(int i=0;i<users.size();i++){
            if(users.get(i).getId()==user.getId()) users.remove(i);
        }
        List<UserWalletTransaction> userWalletTransactionsFrom= userWalletTransactionService.getByFromUser(user,0);
        List<UserWalletTransaction> userWalletTransactionsTo= userWalletTransactionService.getByToUser(user,0);

        model.addAttribute("users",users);
        model.addAttribute("fromMe",userWalletTransactionsFrom);
        model.addAttribute("toMe",userWalletTransactionsTo);
        model.addAttribute("currentUser",user);
        return "borrow-money";
    }


}
