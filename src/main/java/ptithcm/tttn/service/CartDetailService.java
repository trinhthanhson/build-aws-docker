package ptithcm.tttn.service;

import ptithcm.tttn.entity.CartDetail;

import java.util.List;

public interface CartDetailService {
        void updateQuantity(String jwt,CartDetail cartDetail) throws Exception;
        void deleteItemCartDetail(String jwt,CartDetail cartDetail) throws Exception;
        void deleteCartDetail(Long cart_id);
}
