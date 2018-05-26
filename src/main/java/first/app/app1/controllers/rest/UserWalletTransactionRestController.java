package first.app.app1.controllers.rest;

import first.app.app1.daos.UserWalletTransactionDao;
import first.app.app1.data.in.OfficeAssignRequest;
import first.app.app1.data.in.UserWalletTransactionRequest;
import first.app.app1.data.out.NotificationDetails;
import first.app.app1.models.User;
import first.app.app1.models.UserWalletTransaction;
import first.app.app1.service.CartServiceImpl;
import first.app.app1.service.NotificationService;
import first.app.app1.service.UserService;
import first.app.app1.service.UserWalletTransactionService;
import first.app.app1.utils.UserSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Calendar;


@RequestMapping("/rest/borrow")
@RestController
public class UserWalletTransactionRestController {
    @Autowired
    UserWalletTransactionService userWalletTransactionService;

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;


    @PostMapping("/request")
    public boolean walletRequestApprove(@RequestBody UserWalletTransactionRequest request) {
        try{
            User userFrom=userService.findByUserId(request.getUserFrom());
            User userTo=userService.findByUserId(request.getUserTo());
            if(userFrom.getLunchWallet()<request.getAmount()) return false;
            userFrom.setLunchWallet((userFrom.getLunchWallet())-request.getAmount());
            userTo.setLunchWallet(userTo.getLunchWallet()+request.getAmount());
            UserWalletTransaction userWalletTransaction=new UserWalletTransaction();
            userWalletTransaction.setUserFrom(userService.findByUserId(request.getUserFrom()));
            userWalletTransaction.setUserTo(userService.findByUserId(request.getUserTo()));
            userWalletTransaction.setAmount(request.getAmount());
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
            userWalletTransaction.setDate(date);
            userWalletTransactionService.saveTransaction(userWalletTransaction);
            userService.saveUser(userFrom);
            userService.saveUser(userTo);
            NotificationDetails notificationDetails=new NotificationDetails();
            notificationDetails.setTitle("Lunch wallet");

            notificationDetails.setBody("User "+userFrom.getUsername()+ " sent "+request.getAmount()+" HRK to your lunch wallet!");
            notificationDetails.setIcon("img/bag_of_money.jpg");


            notificationService.pushMessage(userTo.getUsername(),notificationDetails);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    @PostMapping("/return-request")
    public boolean returnRequestApprove(@RequestBody UserWalletTransactionRequest request) {
        try{

            UserWalletTransaction userWalletTransaction=userWalletTransactionService.getById(request.getId());
            User userTo=userWalletTransaction.getUserTo();
            User userFrom=userWalletTransaction.getUserFrom();
            if(userTo.getLunchWallet()<userWalletTransaction.getAmount()) return false;
            userTo.setLunchWallet(userTo.getLunchWallet()-userWalletTransaction.getAmount());

            userFrom.setLunchWallet(userFrom.getLunchWallet()+userWalletTransaction.getAmount());

            userWalletTransaction.setCompleted(1);
            userService.saveUser(userFrom);
            userService.saveUser(userTo);
            userWalletTransactionService.saveTransaction(userWalletTransaction);
            NotificationDetails notificationDetails=new NotificationDetails();
            notificationDetails.setTitle("Lunch wallet");

            notificationDetails.setBody("User "+userTo.getUsername()+ " sent "+userWalletTransaction.getAmount()+" HRK to your lunch wallet!");
            notificationDetails.setIcon("img/bag_of_money.jpg");
            notificationService.pushMessage(userFrom.getUsername(),notificationDetails);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
