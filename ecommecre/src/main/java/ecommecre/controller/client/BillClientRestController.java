package ecommecre.controller.client;

import ecommecre.model.entity.Bill;
import ecommecre.model.request.BillClientRequest;
import ecommecre.service.EmailService;
import ecommecre.service.client.BillClientService;
import ecommecre.service.test.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/bill")
public class BillClientRestController {

    @Autowired
    private BillService billService;

    @Autowired
    private BillClientService billClientService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/checkout")
    public ResponseEntity<?> viewCheckout(Model model, @RequestParam("carts") List<Integer> cartDetails) {
        try{
            Bill bill = billService.getProductDetailsByCartDetails(cartDetails);
            return new ResponseEntity<>(bill, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addBill(@RequestBody BillClientRequest request) {
        try{
            Bill bill = billClientService.addBill(request);
            emailService.sendEmailBillWithEmailAnother(bill.getId(), "Đơn hàng " + bill.getCode() + " được tạo thành công", request.getEmail());
            return new ResponseEntity<>(bill, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/find-by-code/{code}")
    public ResponseEntity<?> addBill(@PathVariable("code")String code) {
        try{
            Bill bill = billClientService.getBillByCode(code);
            return new ResponseEntity<>(bill, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
