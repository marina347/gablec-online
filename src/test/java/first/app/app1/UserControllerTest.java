package first.app.app1;

import first.app.app1.controllers.UserController;
import first.app.app1.service.UserService;
import first.app.app1.service.UserWalletTransactionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class UserControllerTest {
    @Mock
    UserService userService;

    @Mock
    UserWalletTransactionService userWalletTransactionService;

    @InjectMocks
    UserController userController;
}
