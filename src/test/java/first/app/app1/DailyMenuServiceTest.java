package first.app.app1;

import first.app.app1.daos.DailyMenuDao;
import first.app.app1.models.DailyMenu;
import first.app.app1.service.DailyMenuService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class DailyMenuServiceTest {
    @Mock
    DailyMenuDao dailyMenuDao;

    @InjectMocks
    DailyMenuService dailyMenuService;

    private DailyMenu dailyMenuTest;
    private DailyMenu dailyMenuTest1;

    @Before
    public void setUp() throws IOException
    {
        MockitoAnnotations.initMocks(this);
        dailyMenuTest=new DailyMenu();
    }

    @Test
    public void getCurrentDayTest(){
        when(dailyMenuDao.getById(7)).thenReturn(dailyMenuTest);
        DailyMenu dailyMenuReturn=dailyMenuService.getCurrentDay(0);
        assertEquals(dailyMenuTest,dailyMenuReturn);

        when(dailyMenuDao.getById(3)).thenReturn(dailyMenuTest1);
        DailyMenu dailyMenuReturn1=dailyMenuService.getCurrentDay(3);
        assertEquals(dailyMenuTest1,dailyMenuReturn1);
    }
}
