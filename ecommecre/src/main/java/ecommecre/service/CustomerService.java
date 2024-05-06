package ecommecre.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ecommecre.model.entity.Voucher;
import ecommecre.model.request.CustomerRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ecommecre.model.entity.Customer;
import ecommecre.model.entity.Employee;
import ecommecre.model.response.CustomerResponse;
import ecommecre.repository.CustomerRepository;

@Service
public class CustomerService {

    // Declare the repository as final to ensure its immutability
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Page<Customer> getPage(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }
    public Optional<Customer> getCustomerByIds(Customer id) {
        return customerRepository.findById(id.getId());
    }


    public Customer customerByid(UUID id) {
        return customerRepository.findById(id).get();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public Customer updateCustomer(CustomerRequest customerRequest) {
        Optional<Customer> cus = customerRepository.findById(customerRequest.id);
        if(cus.isPresent()) {
            Customer cus1 = cus.get();
            cus1.setName(customerRequest.getName());
            cus1.setGender(customerRequest.isGender());
            cus1.setBirth_date(customerRequest.getBirth_date());
            cus1.setPhone(customerRequest.getPhone());
            cus1.setDeleted(false);
            return customerRepository.save(cus1);
        }
        else{
            return null;
        }
    }

    public Customer updateCustomerAcc(CustomerRequest customerRequest) {
        Optional<Customer> cus = customerRepository.findById(customerRequest.id);
        if(cus.isPresent()) {
            Customer cus1 = cus.get();
            cus1.setName(customerRequest.name);
            cus1.setGender(customerRequest.isGender());
            cus1.setBirth_date(customerRequest.getBirth_date());
            cus1.setPhone(customerRequest.getPhone());
            cus1.setDeleted(false);
            return customerRepository.save(cus1);
        }
        else{
            return null;
        }
    }

    public void deleteVoucher(UUID id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            if (customer.get().isDeleted()) {
                customer.get().setDeleted(false);
            } else {
                customer.get().setDeleted(true);
            }
            customerRepository.save(customer.get());
        }
    }

    public List<CustomerResponse> getPageNo(int pageNo) {
        return customerRepository.getPageNo(PageRequest.of(pageNo, 3)).getContent();
    }

    public Customer getCounterCustomer() {
        Optional<Customer> cusOptional = customerRepository.getCounterCustomer("COUNTER");
        if (cusOptional.isPresent()) {
            return cusOptional.get();
        } else {
            Customer customer = new Customer();
            customer.setBirth_date(LocalDate.now());
            customer.setCity("");
            customer.setGender(false);
            customer.setName("COUNTER");
            customer.setStatus(1);
            customer.setBirth_date(LocalDate.now());
            return customerRepository.save(customer);
        }
    }

    // that deptrai viet
    public List<Customer> searchEmployees(String searchText) {
        searchText = searchText.trim().toLowerCase();
        return customerRepository.searchByText(searchText);
    }
    // that deptrai viet
    public Customer getCustomerByAccount(UUID accountId) {
        return customerRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khách hàng với tài khoản ID: " + accountId));
    }
}
