package first.app.app1;

import first.app.app1.daos.UserWalletTransactionDao;
import first.app.app1.models.Restaurant;
import first.app.app1.models.User;
import first.app.app1.models.UserWalletTransaction;
import first.app.app1.service.UserWalletTransactionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserWalletTransactionServiceTest {
    @Mock
    UserWalletTransactionDao userWalletTransactionDao;

    @InjectMocks
    UserWalletTransactionService userWalletTransactionService;

    @Before
    public void setUp() throws IOException
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTransactionTest(){
        UserWalletTransaction transactionTest=new UserWalletTransaction();
        //when(restaurantDao.save(testRes));
        userWalletTransactionService.saveTransaction(transactionTest);
        verify(userWalletTransactionDao, times(1)).save(transactionTest);
    }
    @Test
    public void getByFromUserTest(){
        User user=new User();
        int completed=1;
        List<UserWalletTransaction> listaTest=new ArrayList<>(1);
        listaTest.add(new UserWalletTransaction());
        when(userWalletTransactionDao.findByUserFromAndCompleted(user,completed)).thenReturn(listaTest);
        List<UserWalletTransaction> listaR=userWalletTransactionService.getByFromUser(user,completed);
        assertEquals(listaTest,listaR);
    }
    @Test
    public void getByToUserTest(){
        User user=new User();
        int completed=1;
        List<UserWalletTransaction> listaTest=new ArrayList<>(1);
        listaTest.add(new UserWalletTransaction());
        when(userWalletTransactionDao.findByUserToAndCompleted(user,completed)).thenReturn(listaTest);
        List<UserWalletTransaction> listaR=userWalletTransactionService.getByToUser(user,completed);
        assertEquals(listaTest,listaR);
    }

    @Test
    public void getByIdTest(){
        int id=1;
        UserWalletTransaction transactionTest=new UserWalletTransaction();
        when(userWalletTransactionDao.getById(id)).thenReturn(transactionTest);
        UserWalletTransaction transactionReturn=userWalletTransactionService.getById(id);
        assertEquals(transactionTest,transactionReturn);
    }

}
