package first.app.app1.controllers.rest;

import first.app.app1.data.in.CartItemInData;
import first.app.app1.data.in.OfficeApproveRequest;
import first.app.app1.data.in.OfficeAssignRequest;
import first.app.app1.service.CartServiceImpl;
import first.app.app1.service.OfficeService;
import first.app.app1.service.UserService;
import first.app.app1.utils.UserSessionUtils;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/rest/office")
@RestController
public class OfficeRestController {

    @Autowired
    UserService userService;

    @Autowired
    CartServiceImpl cartService;

    @Autowired
    OfficeService officeService;

    @PostMapping()
    public boolean assignOfficeRequest(@RequestBody OfficeAssignRequest request) {
        try{
            officeService.assignOfficeRequest(UserSessionUtils.getUserUsername(),request.getOfficeId());
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @PostMapping("/request")
    public boolean officeRequestApprove(@RequestBody OfficeApproveRequest request) {
        try{
            officeService.approveOfficeRequest(request.getUserId());
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

}
