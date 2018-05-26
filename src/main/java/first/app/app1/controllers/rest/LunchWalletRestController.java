package first.app.app1.controllers.rest;

import first.app.app1.data.in.LunchWalletRequest;
import first.app.app1.data.out.NotificationDetails;
import first.app.app1.models.User;
import first.app.app1.service.NotificationService;
import first.app.app1.service.OfficeService;
import first.app.app1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/wallet")
@RestController
public class LunchWalletRestController {
    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;


    @PostMapping("/request")
    public boolean assignLunchRequest(@RequestBody LunchWalletRequest request) {
        try{
            User user=userService.findByUserId(request.getUserId());
            user.setLunchWallet(user.getLunchWallet()+request.getLunchWallet());
            userService.saveUser(user);
            NotificationDetails notificationDetails=new NotificationDetails();
            notificationDetails.setTitle("Lunch wallet");
            notificationDetails.setIcon("img/bag_of_money.jpg");
            notificationDetails.setBody("Administrator updated your lunch wallet with "+request.getLunchWallet()+" HRK!");
            notificationService.pushMessage(user.getUsername(),notificationDetails);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


}
