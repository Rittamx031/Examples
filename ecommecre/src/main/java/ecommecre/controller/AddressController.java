package ecommecre.controller;

import ecommecre.model.entity.Address;
import ecommecre.service.AddressService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/customer")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("getAllAddressByIdCustomer/{id_customer}")
    public ResponseEntity<List<Address>> getAllAddressByIdCustomer(@PathVariable UUID id_customer) {
        List<Address> addresses = addressService.getAllAddressByIdCustomer(id_customer);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

}
