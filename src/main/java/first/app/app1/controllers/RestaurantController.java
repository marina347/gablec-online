package first.app.app1.controllers;

import cognitivej.vision.face.scenario.FaceScenarios;
import cognitivej.vision.overlay.CognitiveJColourPalette;
import cognitivej.vision.overlay.RectangleType;
import cognitivej.vision.overlay.builder.ImageOverlayBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.ibm.watson.developer_cloud.discovery.v1.model.AggregationResult;
import com.ibm.watson.developer_cloud.discovery.v1.model.Calculation;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryResponse;
import first.app.app1.daos.CategoryDao;
import first.app.app1.models.*;
import first.app.app1.service.*;
import first.app.app1.utils.*;
import first.app.app1.watson.DiscoveryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.getProperty;

@Controller
public class RestaurantController {

    @Value("${discovery.environmentId}")
    private String environmentId;

    @Value("${discovery.collectionId}")
    private String collectionId;

    @Value("${discovery.username}")
    private String username;

    @Value("${discovery.password}")
    private String password;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    DailyMenuService dailyMenuService;

    @Autowired
    RestaurantPopularity restaurantPopularity;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @Autowired
    CartServiceImpl cartService;

    @Autowired
    FoodService foodService;

    @Autowired
    UserOrderService userOrderService;

    String IMAGE_URL="https://i.kinja-img.com/gawker-media/image/upload/s--_DBGLHVf--/c_scale,f_auto,fl_progressive,q_80,w_800/eibgv7kctah62iddzywm.jpg";

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public void setIMAGE_URL(String IMAGE_URL) {
        this.IMAGE_URL = IMAGE_URL;
    }

    private List<Food>  getPropositions(User user)
    {
        DiscoveryManager discoveryManager = new DiscoveryManager(environmentId,collectionId,username,password);
        discoveryManager.initManager();
        QueryResponse queryResponse = discoveryManager.fetchDataForUser(user.getId());
        List<AggregationResult> aggResult = queryResponse.getAggregations().get(0).getResults();
        int proposedFoodIds[]= aggResult.parallelStream()
                .filter(agg-> ((Calculation)agg.getAggregations().get(0)).getValue()>0)
                .sorted((o1, o2) ->
                        ((Calculation)o2.getAggregations().get(0)).getValue().compareTo(
                                ((Calculation)o1.getAggregations().get(0)).getValue())

                )
                .limit(5)
                .mapToInt(agg -> (int)Double.parseDouble(agg.getKey())).toArray();

        return foodService.findByIdIn(proposedFoodIds);
    }

    @GetMapping("/order")
    public String showRestaurants(Model model)
    {


        FaceScenarios faceScenarios = new FaceScenarios(getProperty("c638e2cf31e04c3e91e8f9fe21ccba3d"),
                getProperty("48fe1fe442c742719f3c11a3463ab2cd"));
        ImageOverlayBuilder imageOverlayBuilder = ImageOverlayBuilder.builder(IMAGE_URL);
        imageOverlayBuilder.outlineFacesOnImage(faceScenarios.findFaces(IMAGE_URL), RectangleType.FULL,
                CognitiveJColourPalette.STRAWBERRY).launchViewer();

        List<Restaurant> restaurants =restaurantPopularity.getRestaurants();
        String username= UserSessionUtils.getUserUsername();
        User user=userService.findByUsername(username);
        List<Category> categories = categoryDao.findAll();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        DailyMenu currentDay=dailyMenuService.getCurrentDay(day-1);

        //List<Food> propositions = getPropositions(user);


        //model.addAttribute("propositions", propositions);
        model.addAttribute("currentDay", currentDay);
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("categories", categories);
        model.addAttribute("user", user);
        String gsonResult = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(cartService.getCart(username).getFoodList().values());

        model.addAttribute("cartFoodList", gsonResult);
        return "order";
    }
    @GetMapping("/admin/view-restaurants")
    public String showRestaurantsToAdmin(Model model)
    {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "admin/view-restaurants";
    }
    @GetMapping("/admin/edit-restaurant/{id}")
    public String editRestaurant(@PathVariable(value="id") int restaurantId, Model model ) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurant",restaurant);
        return "admin/edit-restaurant";
    }
    @PostMapping("/admin/edit-restaurant")
    public String saveRestaurant(@RequestParam("id") int restaurantId, @RequestParam("orderTime") Time orderTime) {
        Restaurant restaurant=restaurantService.getRestaurantById(restaurantId);
        restaurant.setOrderTime(orderTime);
        restaurantService.saveRestaurant(restaurant);
        return "redirect:view-restaurants";
    }


    }


