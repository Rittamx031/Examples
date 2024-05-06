package ecommecre.security.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ecommecre.model.entity.Account;
import ecommecre.model.entity.Customer;
import ecommecre.model.entity.Employee;
import ecommecre.model.entity.Roles;
import ecommecre.model.request.EmployeeSignUpRequest;
import ecommecre.service.AccountService;
import ecommecre.service.CustomerService;
import ecommecre.service.EmployeeService;
import ecommecre.service.RolesService;
import ecommecre.utils.validate.EmailHelper;
import ecommecre.model.request.UserSignUpRequest;

@Service
public class SignUpService {
  @Autowired
  EmployeeService employeeService;
  @Autowired
  AccountService accountService;
  @Autowired
  CustomerService customerService;
  @Autowired
  RolesService roleService;
  @Autowired
  EmailHelper emailHelper;
  @Autowired
  PasswordEncoder encoder;

  public Employee signUpAsEmployee(EmployeeSignUpRequest request) {
    if (!emailHelper.isEmailExists(request.getEmail())) {
      Employee employee = new Employee();
      employee.setBirth_date(request.getBirthDay());
      employee.setEmail(request.getEmail().toLowerCase());
      employee.setGender(request.isSex());
      employee.setName(request.getName());
      employee.setDeleted(false);
      employee.setStatus(1);
      employee.setCreatedAt(LocalDateTime.now());
      employee.setPhone(request.getPhone());
      Roles newEmpRoles = roleService.getEmployeeRole();
      employee.setRoles(newEmpRoles);
      employee.setPassword(encoder.encode(request.getPassword()));
      return employeeService.saveEmployee(employee);
    }
    return null;
  }

  public Account signUpAsUser(UserSignUpRequest request) {
    if (!emailHelper.isEmailExists(request.getEmail())) {
      Customer customer = new Customer();
      customer.setPhone(request.getPhone());
      customer.setName(request.getName());
      customer.setGender(request.isSex());
      customer.setBirth_date(request.getBirthDay());
      customer = customerService.saveCustomer(customer);
      Account account = new Account();
      account.setCustomer(customer);
      account.setEmail(request.getEmail().toLowerCase());
      account.setPassword(encoder.encode(request.getPassword()));
      account.setStatus(1);
      return accountService.saveAccount(account);
    }
    // throw exeption email Email already exists
    return null;
  }

  public Employee forgotEmployeePassword(String Email) {
    return null;
  }

  public Account forgotAccountPassword(String Email) {
    return null;
  }
}
