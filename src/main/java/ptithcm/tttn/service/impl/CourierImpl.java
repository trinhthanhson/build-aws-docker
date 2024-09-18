package ptithcm.tttn.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import ptithcm.tttn.exception.ErrorEnums;
import ptithcm.tttn.exception.TrackingMoreException;
import ptithcm.tttn.request.Courier;
import ptithcm.tttn.request.DetectParams;
import ptithcm.tttn.response.TrackingMoreResponse;
import ptithcm.tttn.service.CouriersService;
import ptithcm.tttn.utils.StrUtils;

import java.io.IOException;
import java.util.List;

@Service
public class CourierImpl extends BaseTracking implements CouriersService {
    public TrackingMoreResponse getAllCouriers() throws IOException{
        String apiPath =  "/couriers/all";
        String body = requestHelper.sendApiRequest(apiPath, "GET",null, null);
        TrackingMoreResponse response = objectMapper.readValue(body, new TypeReference<TrackingMoreResponse<List<Courier>>>() {});
        return response;
    }

    public TrackingMoreResponse detect(DetectParams detectParams) throws TrackingMoreException,IOException {
        if(StrUtils.isEmpty(detectParams.getTrackingNumber())) {
            throw new TrackingMoreException(ErrorEnums.ErrMissingTrackingNumber);
        }
        String apiPath =  "/couriers/detect";
        String body = requestHelper.sendApiRequest(apiPath, "POST",null, detectParams);
        TrackingMoreResponse response = objectMapper.readValue(body, new TypeReference<TrackingMoreResponse<List<Courier>>>() {});
        return response;
    }

}