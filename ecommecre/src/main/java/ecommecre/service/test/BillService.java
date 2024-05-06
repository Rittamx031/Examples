package ecommecre.service.test;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ecommecre.model.entity.Account;
import ecommecre.model.entity.Bill;
import ecommecre.model.entity.BillDetail;
import ecommecre.model.entity.CartDetail;
import ecommecre.model.entity.Customer;
import ecommecre.model.entity.Evaluate;
import ecommecre.model.entity.ProductDetail;
import ecommecre.model.request.EvaluateRequest;
import ecommecre.model.request.EvaluateRequest.EvaluateProduct;
import ecommecre.repository.AccountRepository;
import ecommecre.repository.BillRepository;
import ecommecre.repository.CartDetailRepository;
import ecommecre.repository.CartRepository;
import ecommecre.repository.CustomerRepository;
import ecommecre.repository.ProductDetailRepository;
import ecommecre.service.EmailService;
import ecommecre.service.EvaluateService;
import jakarta.validation.Valid;

@Service("testBillService")
public class BillService {
  @Autowired
  CartRepository cartRepository;
  @Autowired
  CartDetailRepository cartDetailRepository;
  @Autowired
  AccountRepository accountRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  EmailService emailService;
  @Autowired
  EvaluateService evaluateService;

  @Autowired
  ProductDetailRepository productDetailRepository;

  @Autowired
  BillRepository billRepository;

  @Autowired
  private HttpServletRequest request;

  private static final String CART_COOKIE_NAME = "userCart";

  public Bill getCheckOutPage(List<Integer> cartDetails) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUserName = authentication.getName();
      Account account = accountRepository.fillAcccoutbyEmail(currentUserName);
      Bill bill = new Bill();
      bill.setCustomer(account.getCustomer());
      bill.setCustomer_name(account.getCustomer().getName());
      bill.setPhone(account.getCustomer().getPhone());
      bill.setAddress(account.getCustomer().getAddress());
      bill.setStatus(0);
      bill.setLoaiDon(1);
      bill.setStatus_pay(0);
      List<BillDetail> billDetails = cartDetails.stream()
          .map(idcartdetails -> cartDetailRepository.findById(idcartdetails))
          .filter(Optional::isPresent)
          .map(Optional::get)
          .map(cartDetail -> {
            BillDetail billDetail = new BillDetail();
            billDetail.setIdBill(bill);
            billDetail.setProductDetail(cartDetail.getProductDetail());
            billDetail.setQuantity(cartDetail.getQuantity());
            billDetail
                .setTotalMoney(Double.valueOf(cartDetail.getProductDetail().getPrice() * cartDetail.getQuantity()));
            return billDetail;
          })
          .collect(Collectors.toList());
      bill.setBillDetail(billDetails);
      double totalMoney = bill.getBillDetail().stream().mapToDouble(BillDetail::getTotalMoney).sum();
      bill.setTotal_money(totalMoney);
      bill.setDeposit(0d);
      bill.setMoney_ship(0d);
      return bill;
    }
    return null;
  }

  public Bill getProductDetailsByCartDetails(List<Integer> cartDetails) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUserName = authentication.getName();
      Account account = accountRepository.fillAcccoutbyEmail(currentUserName);

      // GEN CODE
      String  oldCode = String.valueOf(billRepository.findNewCodeBill() + 1);
      String newCode = "";

      if(oldCode.length() == 1){
        newCode += "00" + oldCode;
      }else if(oldCode.length() == 2){
        newCode += "0" + oldCode;
      }else{
        newCode += oldCode;
      }

      Bill bill = new Bill();
      bill.setCode("HD" + newCode);
      bill.setStatus(0);
      bill.setLoaiDon(1);
      bill.setStatus_pay(0);
      List<BillDetail> billDetails = cartDetails.stream()
          .map(idcartdetails -> cartDetailRepository.findById(idcartdetails))
          .filter(Optional::isPresent)
          .map(Optional::get)
          .map(cartDetail -> {
            BillDetail billDetail = new BillDetail();
            billDetail.setIdBill(bill);
            billDetail.setProductDetail(cartDetail.getProductDetail());
            if (cartDetail.getProductDetail().getQuantity() < cartDetail.getQuantity()) {
              throw new RuntimeException("Hiện tại số lượng sản phẩm không đủ.");
            }
            billDetail.setQuantity(cartDetail.getQuantity());
            billDetail
                .setTotalMoney(Double.valueOf(cartDetail.getProductDetail().getPrice() * cartDetail.getQuantity()));
            return billDetail;
          })
          .collect(Collectors.toList());
      bill.setBillDetail(billDetails);
      double totalMoney = bill.getBillDetail().stream().mapToDouble(BillDetail::getTotalMoney).sum();
      bill.setTotal_money(totalMoney);
      bill.setDeposit(0d);
      bill.setMoney_ship(0d);
      return bill;
    } else {
      // GEN CODE
      String  oldCode = String.valueOf(billRepository.findNewCodeBill() + 1);
      String newCode = "";

      if(oldCode.length() == 1){
        newCode += "00" + oldCode;
      }else if(oldCode.length() == 2){
        newCode += "0" + oldCode;
      }else{
        newCode += oldCode;
      }

      Bill bill = new Bill();
      bill.setCode("HD" + newCode);
      bill.setStatus(0);
      bill.setLoaiDon(1);
      bill.setStatus_pay(0);
      List<BillDetail> billDetails = cartDetails.stream()
          .map(idcartdetails -> productDetailRepository.findById(idcartdetails))
          .filter(Optional::isPresent)
          .map(Optional::get)
          .map(cartDetail -> {
            BillDetail billDetail = new BillDetail();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
              for (Cookie cookie : cookies) {
                if (CART_COOKIE_NAME.equals(cookie.getName())) {
                  if (cookie.getValue() != null) {
                    if (deserializeCart(cookie.getValue()) != null) {
                      Integer quantity = deserializeCart(cookie.getValue()).get(cartDetail.getId());
                      if (cartDetail.getQuantity() < quantity) {
                        throw new RuntimeException("Hiện tại số lượng sản phẩm không đủ.");
                      }
                      billDetail.setQuantity(quantity);
                      billDetail
                          .setTotalMoney(Double.valueOf(cartDetail.getPrice() * quantity));
                      break;
                    }
                  }

                }
              }
            }
            billDetail.setIdBill(bill);
            billDetail.setProductDetail(cartDetail);
            return billDetail;
          })
          .collect(Collectors.toList());
      bill.setBillDetail(billDetails);
      double totalMoney = bill.getBillDetail().stream().mapToDouble(BillDetail::getTotalMoney).sum();
      bill.setTotal_money(totalMoney);
      bill.setDeposit(0d);
      bill.setMoney_ship(0d);
      return bill;
    }
  }

  public Customer getCustomer() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUserName = authentication.getName();
      Account account = accountRepository.fillAcccoutbyEmail(currentUserName);
      return account.getCustomer();
    }
    return null;
  }

  public Bill cancelInvoice(int idBill) {
    Optional<Bill> bill = billRepository.findById(idBill);
    if (bill.isPresent()) {
      if (bill.get().getStatus() == 1) {
        bill.get().setStatus(6);
        bill.get().getBillDetail().stream().forEach(billdetail -> {
          Optional<ProductDetail> productDetails = productDetailRepository
              .findById(billdetail.getProductDetail().getId());
          if (productDetails.isPresent()) {
            productDetails.get()
                .setQuantity(productDetails.get().getQuantity() + billdetail.getQuantity());
            productDetailRepository.save(productDetails.get());
          }
        });
        if (bill.get().getCustomer() != null) {
          if (bill.get().getCustomer().getAccount() != null) {
            CompletableFuture.runAsync(() -> {
              emailService.sendEmailBill(bill.get().getId(), "Đơn hàng của bạn");
            });
          }
        }
      }
    }
    return null;
  }

  public Bill saveHoaDon(Bill bill) {
    billRepository.save(bill);
    return null;
  }

  public void succeedBill(int idBill) {
    Optional<Bill> bill = billRepository.findById(idBill);
    if (bill.isPresent()) {
      bill.get().getBillDetail().stream().forEach(billdetail -> {
        Optional<ProductDetail> productDetails = productDetailRepository
            .findById(billdetail.getProductDetail().getId());
        if (productDetails.isPresent()) {
          productDetails.get()
              .setQuantity(productDetails.get().getQuantity() - billdetail.getQuantity());
          productDetailRepository.save(productDetails.get());
        }
      });
    }
  }

  public EvaluateRequest getEvaluateRequest(int idBill) {
    Optional<Bill> bill = billRepository.findById(idBill);
    if (bill.isPresent() && bill.get().getStatus() == 5) {
      List<EvaluateProduct> evaluateProducts = bill.get().getBillDetail().stream()
          .map(billdetail -> new EvaluateProduct(
              billdetail.getProductDetail(),
              0,
              ""))
          .collect(Collectors.toList());
      // Assuming you have the customer ID available
      UUID customerId = bill.get().getCustomer().getId();
      return new EvaluateRequest(idBill, customerId, evaluateProducts);
    }
    return null;
  }

  public void saveEvaluate(@Valid EvaluateRequest request) {
    Optional<Bill> bill = billRepository.findById(request.getIdbill());
    Optional<Customer> customer = customerRepository.findById(request.getIdCustomer());
    if (!bill.isPresent()) {
      return;
    }
    if (!customer.isPresent()) {
      return;
    }
    request.getEvaluateProducts().stream().forEach(evaluate -> {
      Optional<ProductDetail> productDetail = productDetailRepository.findById(evaluate.getIdproductdetails().getId());
      if (productDetail.isPresent()) {
        Evaluate evld = new Evaluate();
        evld.setBill(bill.get());
        evld.setCustomer(customer.get());
        evld.setProductDetail(productDetail.get());
        evld.setDescription(evaluate.getDiscription());
        evld.setStart(evaluate.getRating());
        evaluateService.createEvaluate(evld);
      }
    });
  }

  private Map<Integer, Integer> deserializeCart(String cartValue) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String decodedValue = URLDecoder.decode(cartValue, "UTF-8");
      return objectMapper.readValue(decodedValue, new TypeReference<HashMap<Integer, Integer>>() {
      });
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
