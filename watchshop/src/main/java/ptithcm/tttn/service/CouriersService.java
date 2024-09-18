package ptithcm.tttn.service;

import ptithcm.tttn.exception.TrackingMoreException;
import ptithcm.tttn.request.DetectParams;
import ptithcm.tttn.response.TrackingMoreResponse;

import java.io.IOException;

public interface CouriersService {
    TrackingMoreResponse getAllCouriers() throws IOException;

    TrackingMoreResponse detect(DetectParams detectParams) throws TrackingMoreException,IOException;

}
