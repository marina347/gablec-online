package first.app.app1;

import first.app.app1.daos.FoodDao;
import first.app.app1.models.Cart;
import first.app.app1.models.Food;
import first.app.app1.service.CartServiceImpl;
import first.app.app1.service.FoodService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartServiceImplTest {
    @Mock
    FoodDao foodDao;

    @InjectMocks
    CartServiceImpl cartService;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test//(//expected = Exception.class)
    public void findCartSessionTest(){
        Cart cartTest=new Cart();
        String username="test";
        Cart c = cartService.findCartSession(username);
        assertNull(c);
        cartService.destroyCartSession(username); //na kraju svakog jer mi ostane u memoriji

    }
    @Test
    public void getCartTest(){
        String username="test";
        Cart cartTest=cartService.getCart(username);
        assertNotNull(cartTest);

        Cart cartTest1=cartService.getCart(username);
        assertNotNull(cartTest1);
        cartService.destroyCartSession(username);
    }

    @Test
    public void putItemIntoCartTest(){
        String username="test";
        int foodId=1;
        int count=2;
        Food foodTest=new Food();
        foodTest.setId(foodId);
        Cart cartTest=cartService.getCart(username);
        when(foodDao.findById(foodId)).thenReturn(foodTest);
        assertTrue(cartTest.getFoodList().size()==0);
        cartService.putItemIntoCart(username,foodId,count);
        assertTrue(cartTest.getFoodList().size()==1);

        cartService.putItemIntoCart(username,foodId,count);
        assertEquals(cartTest.getFoodList().get(foodId).getCount(),4);
        cartService.destroyCartSession(username);
    }
    @Test
    public void incrementItemCountTest() throws Exception {
        String username="test";
        int foodId=1;
        int count=2;
        Food foodTest=new Food();
        foodTest.setId(foodId);

        Cart cartTest=cartService.getCart(username);
        when(foodDao.findById(foodId)).thenReturn(foodTest);
        cartService.putItemIntoCart(username,foodId,count);
        assertEquals(cartTest.getFoodList().get(foodId).getCount(),count);
        cartService.incrementItemCount(username,foodId);
        assertEquals(cartTest.getFoodList().get(foodId).getCount(),count+1);
        cartService.destroyCartSession(username);
    }

    @Test
    public void decrementItemCountTest() throws Exception {
        String username="test";
        int foodId=1;
        int count=2;
        Food foodTest=new Food();
        foodTest.setId(foodId);

        Cart cartTest=cartService.getCart(username);
        when(foodDao.findById(foodId)).thenReturn(foodTest);
        cartService.putItemIntoCart(username,foodId,count);
        assertEquals(cartTest.getFoodList().get(foodId).getCount(),count);
        cartService.decrementItemCount(username,foodId);
        assertEquals(cartTest.getFoodList().get(foodId).getCount(),count-1);
        cartService.destroyCartSession(username);
    }

    @Test
    public void removeItemFromCartTest() throws Exception {
        String username="test";
        int foodId=1;
        int count=2;
        Food foodTest=new Food();
        foodTest.setId(foodId);

        Cart cartTest=cartService.getCart(username);
        when(foodDao.findById(foodId)).thenReturn(foodTest);
        cartService.putItemIntoCart(username,foodId,count);
        assertEquals(cartTest.getFoodList().get(foodId).getCount(),count);
        cartService.removeItemFromCart(username,foodId);
        assertNull(cartTest.getFoodList().get(foodId));
        cartService.destroyCartSession(username);
    }

    @Test
    public void calculateCartTotal(){
        String username="test";
        int foodId=1;
        int count=2;
        Food foodTest=new Food();
        foodTest.setId(foodId);
        foodTest.setPrice(30);

        Cart cartTest=cartService.getCart(username);
        when(foodDao.findById(foodId)).thenReturn(foodTest);
        cartService.putItemIntoCart(username,foodId,count);
        assertEquals(cartTest.getFoodList().get(foodId).getCount(),count);

        assertEquals(cartService.calculateCartTotal(cartTest),60,0.00001);
        cartService.destroyCartSession(username);
    }

    @Test
    public void destroyCartSession(){
        String username="test";
        boolean rez=cartService.destroyCartSession(username);
        assertFalse(rez);

        cartService.getCart(username);
        boolean rez1=cartService.destroyCartSession(username);
        assertTrue(rez1);

    }


}
