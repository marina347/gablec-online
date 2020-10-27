package first.app.app1;

import first.app.app1.daos.FoodDao;
import first.app.app1.models.Food;
import first.app.app1.service.FoodService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class FoodServiceTest {
    @Mock
    FoodDao foodDao;

    @InjectMocks
    FoodService foodService;

    @Before
    public void setUp() throws IOException
    {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void findByIdInTest() throws Exception{
        int [] polje=new int[1];
        polje[0]=1;
        List<Food> papica=new ArrayList<>(1);
        papica.add(new Food());
        when(foodDao.findByIdIn(polje)).thenReturn(papica);
        List<Food> papicaReturn=foodService.findByIdIn(polje);
        assertEquals(papica,papicaReturn);
    }
}
