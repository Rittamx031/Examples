package ecommecre.model.request;

import ecommecre.model.entity.Bill;
import ecommecre.model.entity.Voucher;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillClientRequest {

    String name;

    String email;

    String phone_number;

    String address;

    String note;

    Bill bill;

    Integer payment_method;

    Voucher coupoun;

    Double ship_fee;

    Double total_money;

}
