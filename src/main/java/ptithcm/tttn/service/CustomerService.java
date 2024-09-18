package ptithcm.tttn.service;

import ptithcm.tttn.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    Customer updateCustomer(Customer customer, String jwt) throws Exception;

    Customer findById(Long id) throws Exception;

    Customer findByUserId(Long id) throws Exception;

    boolean checkEmailExist(String email);

    Customer findByEmail(String email) throws Exception;

}
