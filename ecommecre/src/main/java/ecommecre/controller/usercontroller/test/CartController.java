package ecommecre.controller.usercontroller.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ecommecre.exeption.AuthenticationException;
import ecommecre.model.cookieentity.CartResponse;
import ecommecre.model.entity.Cart;
import ecommecre.service.test.CartCookieService;
import ecommecre.service.test.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller("testCartController")
public class CartController {
  @Autowired
  private CartService cartService;
  @Autowired
  CartCookieService cartcookieService;

  @GetMapping("/shop/cart")
  public String viewCart(Model model, HttpServletRequest request, HttpServletResponse response) {
    try {
      cartService.addCookieCartToUser(request, response);
      Cart cart = cartService.getCart();
      model.addAttribute("cart", cart);
    } catch (AuthenticationException e) {
      List<CartResponse> listcart = cartcookieService.getCartResponses(request, response);
      model.addAttribute("cartcookie", listcart);
    }
    return "user2/cart";
  }
}
