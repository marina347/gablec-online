package first.app.app1;


import first.app.app1.daos.FoodDao;
import first.app.app1.daos.PhoneDao;
import first.app.app1.daos.RestaurantDao;
import first.app.app1.models.Restaurant;
import first.app.app1.service.RestaurantService;
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

public class RestaurantServiceTest {
    @Mock
    RestaurantDao restaurantDao;

    @Mock
    FoodDao foodDao;

    @Mock
    PhoneDao phoneDao;

    @InjectMocks
    RestaurantService restaurantService;



    @Before
    public void setUp() throws IOException
    {
        MockitoAnnotations.initMocks(this); //kreira restaurant serviec objekt i u njega meče mokove
    }

    @Test
    public void getAllRestaurantsTest() throws Exception
    {
        List<Restaurant> list= new ArrayList<>(1);
        list.add(new Restaurant());
        when(restaurantDao.findAll()).thenReturn(list);
        List<Restaurant> lista1= restaurantService.getAllRestaurants();
        assertEquals(list,lista1);
    }
    @Test
    public void getRestaurantByIdTest() throws Exception
    {
        int id=1;
        Restaurant restac=new Restaurant();
        when(restaurantDao.findById(id)).thenReturn(restac);
        Restaurant restaurant=restaurantService.getRestaurantById(id);
        assertEquals(restac,restaurant);

    }

    @Test
    public void saveRestaurant() throws Exception{
        Restaurant testRes=new Restaurant();
        //when(restaurantDao.save(testRes));
        restaurantService.saveRestaurant(testRes);
        verify(restaurantDao, times(1)).save(testRes);
    }




}
