package ptithcm.tttn.service;

import ptithcm.tttn.entity.Cart;
import ptithcm.tttn.request.AddItemRequest;

public interface CartService {

    Cart findCartByJwtCustomer(String jwt) throws Exception;
    void autoUpdateCart(Long cart_id) throws Exception;
    Cart findById(Long id) throws Exception;
    String addCartItem(String jwt, AddItemRequest rq) throws Exception;
}
