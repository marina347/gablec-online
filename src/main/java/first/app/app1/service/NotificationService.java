package first.app.app1.service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Notification;
import com.google.android.gcm.server.Sender;
import first.app.app1.data.out.NotificationDetails;
import first.app.app1.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class NotificationService {
    @Autowired
    UserService userService;

    private static final String apiKey = "AAAAgvrDWng:APA91bE1gPVdpmz04F9dwc4rkZuWysx87qdqjRe74ihTJSNYKbx1oi2u7xrOL388ag-ljgVy0FRMqDJHtFskNVohYAyRE_aQ9OJOzU60JExMfzeOoVzf6Rf8DREDQtwGpY3rs4H46rGG";
    private static final HashMap<String, NotificationDetails> notificationsMapping = new HashMap<>();

    public NotificationService() {

    }

    private void pushMessage(String userGoogleId)
    {
        Sender sender= new Sender(apiKey);
        //Notification n = new Notification.Builder("asdasdas").body("BODY").build();
        Message message = new Message.Builder().build();
        try {
            sender.sendNoRetry(message, userGoogleId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pushMessage(String username, NotificationDetails notificationDetails)
    {
        synchronized(notificationsMapping)
        {
            notificationsMapping.put(username, notificationDetails);
        }
        String userGoogleId = userService.findByUsername(username).getGoogleId();
        pushMessage(userGoogleId);
    }

    public void pushBulkMessages(List<User> users, NotificationDetails notificationDetails)
    {
        for(User user: users)
        {
            synchronized(notificationsMapping)
            {
                notificationsMapping.put(user.getUsername(), notificationDetails);
            }
            pushMessage(user.getGoogleId());
        }
    }

    public void pushBulkMessages(Set<String> users, NotificationDetails notificationDetails)
    {
        List<User> userList = userService.findByUsernames(users);
        this.pushBulkMessages(userList, notificationDetails);
    }

    public NotificationDetails getMessage(String username)
    {
        //we don't return null to avoid nullptr exceptions
        return notificationsMapping.containsKey(username)?
                notificationsMapping.get(username):
                new NotificationDetails("","","");
    }





}
