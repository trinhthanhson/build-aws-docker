package ptithcm.tttn.controller.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.tttn.request.CreateTrackingParams;
import ptithcm.tttn.request.OrderRequest;
import ptithcm.tttn.response.TrackingMoreResponse;
import ptithcm.tttn.service.impl.TrackingImpl;

@RestController
@RequestMapping("/api/staff/tracking")
public class StaffTrackingController {

    private final TrackingImpl trackingService;


    @Autowired
    public StaffTrackingController(TrackingImpl trackingService) {
        this.trackingService = trackingService;
    }

    @PostMapping("/delivery")
    public TrackingMoreResponse trackOrder(@RequestBody OrderRequest orderRequest) throws Exception {
        return trackingService.CreateTracking(orderRequest);
    }
}
