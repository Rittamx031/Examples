package ecommecre.service.client;

import ecommecre.model.entity.Voucher;
import ecommecre.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherClientService {

    @Autowired
    private VoucherRepository voucherRepository;

    public Object findVoucherByCode(String code){
        Voucher voucher = null;
        ArrayList<Voucher> vouchers = (ArrayList<Voucher>) voucherRepository.findAll();
        for (Voucher e : vouchers) {
            if(e.getCode().equals(code)){
                voucher = e;
            }
        }

        if(voucher == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }else{
            return voucher;
        }
    }

}
