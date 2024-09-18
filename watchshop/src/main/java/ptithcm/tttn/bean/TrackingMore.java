package ptithcm.tttn.bean;


import ptithcm.tttn.exception.ErrorEnums;
import ptithcm.tttn.exception.TrackingMoreException;
import ptithcm.tttn.service.CouriersService;
import ptithcm.tttn.service.TrackingService;
import ptithcm.tttn.service.impl.CourierImpl;
import ptithcm.tttn.service.impl.TrackingImpl;

public class TrackingMore {

    public static String apiKey = "7zlv6djs-5hhh-euqh-e40g-mdbvp3bt4ogc";

    public CouriersService couriers;

    public TrackingService trackings;

    public TrackingMore(String apiKey) throws TrackingMoreException {
         if(apiKey == ""){
             throw new TrackingMoreException(ErrorEnums.ErrEmptyAPIKey);
         }
         this.apiKey = apiKey;
         this.couriers = new CourierImpl();
         this.trackings = new TrackingImpl();
    }
}
