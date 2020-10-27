package first.app.app1;

import first.app.app1.daos.OfficeDao;
import first.app.app1.daos.UserDao;
import first.app.app1.models.Office;
import first.app.app1.models.User;
import first.app.app1.service.OfficeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OfficeServiceTest {
    @Mock
    OfficeDao officeDao;

    @Mock
    UserDao userDao;

    @InjectMocks
    OfficeService officeService;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void assignOfficeRequestTest(){
        String username="test";
        int officeId=1;
        User userTest=new User();
        Office officeTest=new Office();
        officeTest.setId(officeId);
        when(userDao.findByUsername(username)).thenReturn(userTest);
        when(officeDao.findById(officeId)).thenReturn(officeTest);
        //when(userDao.save(userTest));
        officeService.assignOfficeRequest(username,officeId);
/*
        verify(userDao,times(1)).findByUsername(username);
        verify(officeDao,times(1)).findById(officeId);*/
        verify(userDao,times(1)).save(userTest);

        assertEquals(userTest.getOffice(),officeTest);
        assertEquals(userTest.getOffice().getId(),officeTest.getId());
    }

    @Test
    public void approveOfficeRequest(){
        int userId=1;
        User userTest=new User();
        when(userDao.findById(userId)).thenReturn(userTest);
        officeService.approveOfficeRequest(userId);
        verify(userDao,times(1)).save(userTest);

        assertEquals(userTest.getOdobreno(),1);
    }

    @Test
    public void getAllOfficesTest(){
        List<Office> listTest=new ArrayList<>(1);
        listTest.add(new Office());
        when(officeDao.findAll()).thenReturn(listTest);
        List<Office> listReturn=officeService.getAllOffices();
        assertEquals(listTest,listReturn);
    }


}
