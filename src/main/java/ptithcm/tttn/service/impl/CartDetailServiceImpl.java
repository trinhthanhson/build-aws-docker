package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Cart;
import ptithcm.tttn.entity.CartDetail;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.CartDetailRepo;
import ptithcm.tttn.service.CartDetailService;
import ptithcm.tttn.service.CartService;
import ptithcm.tttn.service.CustomerService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    private final UserService userService;
    private final CustomerService customerService;
    private final CartService cartService;
    private final CartDetailRepo cartDetailRepo;

    public CartDetailServiceImpl(UserService userService, CustomerService customerService, CartService cartService, CartDetailRepo cartDetailRepo) {
        this.userService = userService;
        this.customerService = customerService;
        this.cartService = cartService;
        this.cartDetailRepo = cartDetailRepo;
    }

    @Override
    @Transactional
    public void updateQuantity(String jwt, CartDetail cartDetail) throws Exception {
       Cart cart = cartService.findCartByJwtCustomer(jwt);
       if(cart != null){
           cartDetailRepo.updateQuantity(cart.getCart_id(),cartDetail.getProduct_id(),cartDetail.getQuantity());
           cartService.autoUpdateCart(cart.getCart_id());
       }else{
           throw new Exception("not found cart");
       }
    }

    @Override
    public void deleteItemCartDetail(String jwt,CartDetail cartDetail) throws Exception {
        Cart cart = cartService.findCartByJwtCustomer(jwt);
        if(cart != null){
            cartDetailRepo.deleteItemCartDetail(cartDetail.getProduct_id(),cart.getCart_id());
        }else{
            throw new Exception("Can not delete item in cart ");
        }

    }

    @Override
    @Transactional
    public void deleteCartDetail(Long cart_id) {
        cartDetailRepo.deleteCartDetail(cart_id);
    }
}
