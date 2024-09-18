package ptithcm.tttn.controller.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Cart;
import ptithcm.tttn.entity.CartDetail;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.request.AddItemRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.service.CartDetailService;
import ptithcm.tttn.service.CartService;
import ptithcm.tttn.service.UserService;

@RestController
@RequestMapping("/api/customer/cart")
public class CartCustomerController {

    private final CartService cartService;

    private final CartDetailService cartDetailService;

    public CartCustomerController(CartService cartService, CartDetailService cartDetailService) {
        this.cartService = cartService;
        this.cartDetailService = cartDetailService;
    }

    @GetMapping("/")
    public ResponseEntity<EntityResponse<Cart>> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws Exception {
        EntityResponse res = new EntityResponse();
        try{
            Cart cart = cartService.findCartByJwtCustomer(jwt);
            if(cart != null){
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
                res.setData(cart);
                res.setMessage("success");
            }else{
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
                res.setData(null);
                res.setMessage("fail");
            }
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setData(null);
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt){


        ApiResponse res = new ApiResponse();
        try{
                cartService.addCartItem(jwt,req);
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
                res.setMessage("Add cart item success");
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @PutMapping("/update/quantity")
    public ResponseEntity<ApiResponse> updateQuantityCartByCustomer(@RequestHeader("Authorization") String jwt, @RequestBody CartDetail cartDetail){
        ApiResponse res = new ApiResponse();
        try{
            cartDetailService.updateQuantity(jwt,cartDetail);
            res.setMessage("success");
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        }catch (Exception e){
            res.setMessage("error " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PostMapping("/delete/item")
    public ResponseEntity<ApiResponse> deleteItemCartByCustomer(@RequestHeader("Authorization") String jwt, @RequestBody CartDetail cartDetail){
        ApiResponse res = new ApiResponse();
        try{
            cartDetailService.deleteItemCartDetail(jwt,cartDetail);
            res.setMessage("success");
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        }catch (Exception e){
            res.setMessage("error " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

}
