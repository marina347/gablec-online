package first.app.app1.service;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;
import first.app.app1.models.CartItem;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.*;

public class SMSService {
    public static final String ACCOUNT_SID = "ACcbc9f8d9cd859dbd3b65c6c6b00492cd";
    public static final String AUTH_TOKEN = "f934defa82de24a10616ff34ddb53e5a";
    public static final String TWILIO_NUMBER = "+38551770186 ";


    public void sendSMS(Collection<CartItem> col){

        AuthMethod auth = new TokenAuthMethod("75fdcc56", "IczPUQLKC1dEucfX");
        NexmoClient client = new NexmoClient(auth);

        List<CartItem> list = new ArrayList<CartItem>(col);
        HashMap<String,Integer> newList=new HashMap<>();

        String porukica="ecx.io"+System.lineSeparator();
        for(int i=0;i<list.size();i++){

            porukica+=list.get(i).getFood().getName()+" x"+list.get(i).getCount()+System.lineSeparator();

        }

        TextMessage message = new TextMessage("385976207789", "385976207789", porukica);

        //There may be more than one response if the SMS sent is more than 160 characters.
        SmsSubmissionResult[] responses=null;


        try {
            responses = client.getSmsClient().submitMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NexmoClientException e) {
            e.printStackTrace();
        }



    }
}
