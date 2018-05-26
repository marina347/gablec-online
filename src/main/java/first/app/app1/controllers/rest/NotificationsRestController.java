package first.app.app1.controllers.rest;

import first.app.app1.data.in.NotifDataRequest;
import first.app.app1.data.out.NotificationDetails;
import first.app.app1.models.User;
import first.app.app1.service.NotificationService;
import first.app.app1.service.UserService;
import first.app.app1.utils.UserSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/rest/notifications")
@RestController
public class NotificationsRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public boolean assignGoogleId(@RequestBody NotifDataRequest request) {
        try{
            User user=userService.findByUsername(UserSessionUtils.getUserUsername());
            user.setGoogleId(request.getGoogleId());
            userService.saveUser(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @GetMapping
    public ResponseEntity<NotificationDetails> fetchMessage(@RequestParam String username)
    {
        try
        {
            return new ResponseEntity<NotificationDetails>(
                    notificationService.getMessage(username),
                    HttpStatus.OK) ;
        }
        catch (Exception e)
        {
            return new ResponseEntity<NotificationDetails>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
