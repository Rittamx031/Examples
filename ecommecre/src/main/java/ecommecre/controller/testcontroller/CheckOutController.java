package ecommecre.controller.testcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecommecre.model.entity.Account;
import ecommecre.model.entity.Bill;
import ecommecre.repository.AccountRepository;
import ecommecre.service.VoucherService;
import ecommecre.service.test.BillService;
import ecommecre.service.test.CartService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("testCheckoutcontroler")
@RequestMapping("/test/checkout")
public class CheckOutController {

  @Autowired
  private VoucherService voucherService;
  @Autowired
  private AccountRepository accountService;
  @Autowired
  private BillService billService;

  @GetMapping
  public String viewCheckout(Model model, @RequestParam("carts") List<Integer> cartDetails) {
    Bill bill = billService.getCheckOutPage(cartDetails);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Account khachHang = accountService.getAccountByEmails(authentication.getName());
    model.addAttribute("khachHang", khachHang);
    model.addAttribute("voucher", voucherService.getAllVoucherAble());
    model.addAttribute("bills", bill);
    model.addAttribute("cartDetails", cartDetails);
    return "user/checkout.html";
  }
}
