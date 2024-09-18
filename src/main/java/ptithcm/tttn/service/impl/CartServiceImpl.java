package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.*;
import ptithcm.tttn.repository.CartDetailRepo;
import ptithcm.tttn.repository.CartRepo;
import ptithcm.tttn.request.AddItemRequest;
import ptithcm.tttn.service.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final UserService userService;
    private final CustomerService customerService;
    private final CartRepo cartRepo;
    private final CartDetailRepo cartDetailRepo;
    private final ProductService productService;

    public CartServiceImpl(UserService userService, CustomerService customerService, CartRepo cartRepo, CartDetailRepo cartDetailRepo, ProductService productService) {
        this.userService = userService;
        this.customerService = customerService;
        this.cartRepo = cartRepo;
        this.cartDetailRepo = cartDetailRepo;
        this.productService = productService;
    }

    @Override
    public Cart findCartByJwtCustomer(String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        Cart cart = cartRepo.findCartByJwtCustomer(customer.getCustomer_id());
        autoUpdateCart(cart.getCart_id());
        return cart;

    }

    @Override
    public void autoUpdateCart(Long cart_id) throws Exception {
        // TODO Auto-generated method stub
        Cart cart = findById(cart_id);

        List<CartDetail> cartDetail = cartDetailRepo.findCartDetailByCartId(cart_id);
        if(cartDetail.isEmpty()){
            cart.setTotal_price(0);
            cart.setTotal_quantity(0);
            cartRepo.save(cart);
        }else{
            int totalPrice = cartDetailRepo.totalPriceByCartId(cart.getCart_id());
            int totalQuantity = cartDetailRepo.totalQuantityByCartId(cart.getCart_id());
            cart.setTotal_price(totalPrice);
            cart.setTotal_quantity(totalQuantity);
            cartRepo.save(cart);
        }
    }

    @Override
    public Cart findById(Long id) throws Exception {
        Optional<Cart> cart = cartRepo.findById(id);

        if (cart.isPresent()) {
            return cart.get();
        }
        throw new Exception("Cart not found with id " + cart);
    }

    @Override
    @Transactional
    public String addCartItem(String jwt, AddItemRequest rq) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        Cart cart = cartRepo.findCartByJwtCustomer(customer.getCustomer_id());
        Product product = productService.findByName(rq.getProduct_name());
        CartDetail cartDetail = cartDetailRepo.findCartDetailByProduct(cart.getCart_id(),product.getProduct_id());
        if(cartDetail != null){
            cartDetail.setPrice(rq.getPrice());
            cartDetail.setQuantity(cartDetail.getQuantity() + 1);
            cartDetailRepo.save(cartDetail);
            int totalPrice = cartDetailRepo.totalPriceByCartId(cart.getCart_id());
            int totalQuantity = cartDetailRepo.totalQuantityByCartId(cart.getCart_id());
            cart.setTotal_price(totalPrice);
            cart.setTotal_quantity(totalQuantity);
            cartRepo.save(cart);
        }else{
            CartDetail create = new CartDetail();
            create.setProduct_id(product.getProduct_id());
            create.setCart_id(cart.getCart_id());
            create.setPrice(rq.getPrice());
            create.setQuantity(1);
            cartDetailRepo.save(create);
            int totalPrice = cartDetailRepo.totalPriceByCartId(cart.getCart_id());
            int totalQuantity = cartDetailRepo.totalQuantityByCartId(cart.getCart_id());
            cart.setTotal_price(totalPrice);
            cart.setTotal_quantity(totalQuantity);
            cartRepo.save(cart);
        }
        return "Item add to cart";
    }
}
