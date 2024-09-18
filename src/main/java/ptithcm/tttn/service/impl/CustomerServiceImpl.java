package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.CustomerRepo;
import ptithcm.tttn.service.CustomerService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final UserService userService;

    public CustomerServiceImpl(CustomerRepo customerRepo, UserService userService) {
        this.customerRepo = customerRepo;
        this.userService = userService;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    @Override
    @Transactional
    public Customer updateCustomer(Customer customer,String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer find = findByUserId(user.getUser_id());
        if(find != null){
            try{
                find.setUpdated_at(LocalDateTime.now());
                find.setFirst_name(customer.getFirst_name());
                find.setPhone(customer.getPhone());
                find.setEmail(customer.getEmail());
                find.setLast_name(customer.getLast_name());
                find.setBirthday(customer.getBirthday());
                find.setGender(customer.getGender());
                find.setTax_id(customer.getTax_id());
                find.setAddress(customer.getAddress());
                find.setCitizen_id(customer.getCitizen_id());
                return customerRepo.save(find);
            }catch (Exception e){
                throw new Exception("error " + e.getMessage());
            }
        }else {
            throw new Exception("not found info customer!. Please connect to admin");
        }

    }

    @Override
    public Customer findById(Long id) throws Exception {
        Optional<Customer> find = customerRepo.findById(id);
        if(find.isPresent()){
            return find.get();
        }
        throw new Exception("not found customer by id " + id);
    }

    @Override
    public Customer findByUserId(Long id) throws Exception {
        Customer find = customerRepo.findByUserId(id);
        if(find != null){
            return find;
        }
        throw new Exception("not found customer by user id " + id);
    }


    @Override
    public boolean checkEmailExist(String email) {
        Customer user = customerRepo.findByEmail(email);
        if(user != null){
            return true;
        }
        return false;
    }

    @Override
    public Customer findByEmail(String email) throws Exception {
        Customer customer = customerRepo.findByEmail(email);
        if(customer != null){
            return customer;
        }
        throw new Exception("not found customer by email " +email);
    }
}
