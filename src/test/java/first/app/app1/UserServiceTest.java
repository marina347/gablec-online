package first.app.app1;

import first.app.app1.daos.UserDao;
import first.app.app1.models.Restaurant;
import first.app.app1.models.User;
import first.app.app1.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    UserDao userDao;

    @InjectMocks
    UserService userService;

    private List<User> listTest;
    private User userTest;

    @Before
    public void setUp() throws IOException
    {
        MockitoAnnotations.initMocks(this);
        listTest=new ArrayList<>(1);
        listTest.add(new User());
        userTest=new User();

    }
    @Test
    public void findByUserIdTest(){
        int id=1;
        when(userDao.findById(id)).thenReturn(userTest);
        User userReturn=userDao.findById(id);
        assertEquals(userTest,userReturn);
    }

    @Test
    public void findByUsernameTest(){
        String username="marina";
        when(userDao.findByUsername(username)).thenReturn(userTest);
        User userReturn=userDao.findByUsername(username);
        assertEquals(userTest,userReturn);
    }

    @Test
    public void getRequestsForOfficesTest(){
        int odobreno=1;
        when(userDao.findByOdobrenoAndOfficeIsNotNull(odobreno)).thenReturn(listTest);
        List<User> listReturn=userDao.findByOdobrenoAndOfficeIsNotNull(odobreno);
        assertEquals(listTest,listReturn);
    }

    @Test
    public void saveUserTest(){
        userService.saveUser(userTest);
        verify(userDao, times(1)).save(userTest);
    }

    @Test
    public void getUsersByOfficeIdTest(){
        int officeId=1;
        when(userDao.findByOfficeId(officeId)).thenReturn(listTest);
        List<User> listReturn=userDao.findByOfficeId(officeId);
        assertEquals(listTest,listReturn);
    }

    @Test
    public void findByUsernamesTest(){
        Set<String> usernames=new HashSet<>(1);
        //usernames.add("marina");
        when(userDao.findByUsernameIn(usernames)).thenReturn(listTest);
        List<User> listaReturn=userDao.findByUsernameIn(usernames);
        assertEquals(listTest,listaReturn);

    }
}
