package ptithcm.tttn.service;

import ptithcm.tttn.entity.PriceUpdateDetail;

public interface PriceUpdateDetailService {
    PriceUpdateDetail updatePriceProduct(String id,PriceUpdateDetail priceUpdateDetail, String jwt) throws Exception;

}
