package first.app.app1.controllers;

import first.app.app1.models.Cart;
import first.app.app1.models.User;
import first.app.app1.service.CartService;
import first.app.app1.service.PaymentMethodService;
import first.app.app1.service.UserService;
import first.app.app1.utils.UserSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {
    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    PaymentMethodService paymentMethodService;

    @GetMapping("/checkout")
    public String showCheckout(Model model)
    {
        User user = userService.findByUsername(UserSessionUtils.getUserUsername());
        Cart cart = cartService.getCart(UserSessionUtils.getUserUsername());
        model.addAttribute("cartItems", cart.getFoodList().values());
        model.addAttribute("paymentMethods", paymentMethodService.findAll());
        model.addAttribute("total", cartService.calculateCartTotal(cart));
        model.addAttribute("user", user);
        return "checkout";
    }

}
