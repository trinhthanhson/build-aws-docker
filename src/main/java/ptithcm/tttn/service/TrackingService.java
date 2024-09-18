package ptithcm.tttn.service;

import ptithcm.tttn.exception.TrackingMoreException;
import ptithcm.tttn.request.CreateTrackingParams;
import ptithcm.tttn.request.GetTrackingResultsParams;
import ptithcm.tttn.request.OrderRequest;
import ptithcm.tttn.request.UpdateTrackingParams;
import ptithcm.tttn.response.TrackingMoreResponse;

import java.io.IOException;
import java.util.List;

public interface TrackingService {
    TrackingMoreResponse CreateTracking(OrderRequest createTrackingParams) throws TrackingMoreException, IOException;

    TrackingMoreResponse GetTrackingResults(GetTrackingResultsParams trackingResultsParams) throws IOException;

    TrackingMoreResponse BatchCreateTrackings(List<CreateTrackingParams> paramsList) throws TrackingMoreException,IOException;

    TrackingMoreResponse UpdateTrackingByID(String idSting, UpdateTrackingParams updateTrackingParams) throws TrackingMoreException,IOException;

    TrackingMoreResponse DeleteTrackingByID(String idSting) throws TrackingMoreException,IOException;

    TrackingMoreResponse RetrackTrackingByID(String idSting) throws TrackingMoreException,IOException;
}
