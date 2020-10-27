package first.app.app1;

import first.app.app1.controllers.LoginController;
import first.app.app1.models.User;
import first.app.app1.security.UserDetailsServiceImpl;
import first.app.app1.service.NotificationService;
import first.app.app1.service.OfficeService;
import first.app.app1.service.TodayOrdersService;
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

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class LoginControllerTest {
    @Mock
    UserService userService;

    @Mock
    OfficeService officeService;

    @Mock
    TodayOrdersService todayOrdersService;

    @Mock
    NotificationService notificationService;

    @Autowired
    protected WebApplicationContext context;

    @InjectMocks
    LoginController loginController;

    protected MockMvc mockMvc;
    @Before
    public void setup()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).setViewResolvers(viewResolver).build();

        SecurityContextHolder.getContext().setAuthentication(UserDetailsServiceImpl.getAuthentication("admin","admin", "ROLE_ADMMIN"));
    }

    @Test
    public void showLoginPageTest() throws Exception
    {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void loginErrorTest() throws Exception
    {
        mockMvc.perform(get("/login-error"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("message", "Unsuccessful login! Try with different username or password!"));
    }

    @Test
    public void homeTest() throws Exception
    {
        User user=new User();
        user.setRole("ROLE_ADMIN");
        when(userService.findByUsername("admin")).thenReturn(user);
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:order"));

        //SecurityContextHolder.getContext().setAuthentication(UserDetailsServiceImpl.getAuthentication("admin","admin", "ROLE_CUSTOMER"));

        User user1=new User();
        user1.setRole("ROLE_CUSTOMER");
        when(userService.findByUsername("admin")).thenReturn(user1);
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("office"));
    }

    @Test
    public void logoutTest() throws Exception
    {

        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }
}
