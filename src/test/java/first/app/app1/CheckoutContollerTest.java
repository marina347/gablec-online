package first.app.app1;

import first.app.app1.controllers.CheckoutController;
import first.app.app1.models.Cart;
import first.app.app1.models.User;
import first.app.app1.security.UserDetailsServiceImpl;
import first.app.app1.service.CartService;
import first.app.app1.service.PaymentMethodService;
import first.app.app1.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class CheckoutContollerTest {
    @Mock
    UserService userService;

    @Mock
    CartService cartService;

    @Mock
    PaymentMethodService paymentMethodService;

    @InjectMocks
    CheckoutController checkoutController;

    protected MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext context;
    @Before
    public void setup()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        mockMvc = MockMvcBuilders.standaloneSetup(checkoutController).setViewResolvers(viewResolver).build();

        SecurityContextHolder.getContext().setAuthentication(UserDetailsServiceImpl.getAuthentication("admin","admin", "ROLE_ADMMIN"));
    }

    @Test
    public void showCheckoutTest() throws Exception
    {
        User user=new  User();
        Cart cart=new Cart();
        when(userService.findByUsername("admin")).thenReturn(user);
        when(cartService.getCart("admin")).thenReturn(cart);
        mockMvc.perform(get("/checkout"))
                .andExpect(status().isOk())
                .andExpect(view().name("checkout")).andExpect(model().attribute("user",user));

    }
}
