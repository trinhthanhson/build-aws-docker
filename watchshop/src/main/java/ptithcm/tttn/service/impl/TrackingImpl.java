package ptithcm.tttn.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import ptithcm.tttn.exception.ErrorEnums;
import ptithcm.tttn.exception.TrackingMoreException;
import ptithcm.tttn.function.GenerateOtp;
import ptithcm.tttn.request.*;
import ptithcm.tttn.response.TrackingMoreResponse;
import ptithcm.tttn.service.TrackingService;
import ptithcm.tttn.utils.StrUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TrackingImpl extends BaseTracking implements TrackingService {

    public TrackingMoreResponse CreateTracking(OrderRequest orderRequest) throws TrackingMoreException,IOException{

        CreateTrackingParams create = new CreateTrackingParams();
        create.setCustomerName(orderRequest.getRecipient_name());
        create.setTrackingNumber(GenerateOtp.generateOTP());
        create.setLanguage("en");
        create.setOriginCountryIso2("VN");
        create.setTrackingDestinationCountry(orderRequest.getAddress());
        create.setCourierCode("usps");
        LocalDateTime now = LocalDateTime.now();
        // Lấy thời gian hiện tại

        // Thêm 7 ngày vào thời gian hiện tại
        LocalDateTime futureDate = now.plusDays(7);

        // Chuyển đổi LocalDateTime thành chuỗi theo định dạng yyyy/MM/dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String formattedDate = now.atZone(ZoneOffset.UTC).format(formatter);

        String formattedDate1 = futureDate.atZone(ZoneOffset.UTC).format(formatter);
        create.setOrderDate(formattedDate);
        create.setDestinationCountryIso2("VN");
        create.setTrackingDestinationCountry("VN");
        create.setNote(orderRequest.getAddress());
        create.setCustomerSms(orderRequest.getRecipient_phone());
        create.setTrackingOriginCountry("VN");
        create.setLogisticsChannel(orderRequest.getAddress());
        create.setTrackingShipDate(formattedDate1);
        create.setTitle("Giao Hàng tiện lợi");
        create.setTrackingPostalCode("71300");
        create.setOrigin_info(null);
        if(StrUtils.isEmpty(create.getTrackingNumber())) {
            throw new TrackingMoreException(ErrorEnums.ErrMissingTrackingNumber);
        }
        if(StrUtils.isEmpty(create.getCourierCode())) {
            throw new TrackingMoreException(ErrorEnums.ErrMissingCourierCode);
        }
        String apiPath =  "/trackings/create";
        String body = requestHelper.sendApiRequest(apiPath, "POST",null, create);
        TrackingMoreResponse response = objectMapper.readValue(body, new TypeReference<TrackingMoreResponse<Tracking>>() {});
        return response;
    }

    public TrackingMoreResponse GetTrackingResults(GetTrackingResultsParams trackingResultsParams) throws IOException{
        String apiPath =  "/trackings/get";
        String body = requestHelper.sendApiRequest(apiPath, "GET", trackingResultsParams,null);
        TrackingMoreResponse response = objectMapper.readValue(body, new TypeReference<TrackingMoreResponse<List<Tracking>>>() {});
        return response;
    }

    public TrackingMoreResponse BatchCreateTrackings(List<CreateTrackingParams> paramsList) throws TrackingMoreException,IOException{
        if(paramsList.size() > 40){
            throw new TrackingMoreException(ErrorEnums.ErrMaxTrackingNumbersExceeded);
        }

        for (CreateTrackingParams params : paramsList) {
            if(StrUtils.isEmpty(params.getTrackingNumber())){
                throw new TrackingMoreException(ErrorEnums.ErrMissingTrackingNumber);
            }
            if(StrUtils.isEmpty(params.getCourierCode())){
                throw new TrackingMoreException(ErrorEnums.ErrMissingCourierCode);
            }
        }

        String apiPath =  "/trackings/batch";
        String body = requestHelper.sendApiRequest(apiPath, "POST",null, paramsList);
        TrackingMoreResponse response = objectMapper.readValue(body, new TypeReference<TrackingMoreResponse<BatchResults>>() {});
        return response;
    }

    public TrackingMoreResponse UpdateTrackingByID(String idSting, UpdateTrackingParams updateTrackingParams) throws TrackingMoreException,IOException{
        if(StrUtils.isEmpty(idSting)){
            throw new TrackingMoreException(ErrorEnums.ErrEmptyId);
        }
        String apiPath =  "/trackings/update/"+idSting;
        String body = requestHelper.sendApiRequest(apiPath, "PUT",null, updateTrackingParams);
        TrackingMoreResponse response = objectMapper.readValue(body, new TypeReference<TrackingMoreResponse<UpdateTracking>>() {});
        return response;
    }

    public TrackingMoreResponse DeleteTrackingByID(String idSting) throws TrackingMoreException,IOException{
        if(StrUtils.isEmpty(idSting)){
            throw new TrackingMoreException(ErrorEnums.ErrEmptyId);
        }
        String apiPath =  "/trackings/delete/"+idSting;
        String body = requestHelper.sendApiRequest(apiPath, "DELETE",null, null);
        TrackingMoreResponse response = objectMapper.readValue(body, new TypeReference<TrackingMoreResponse<Tracking>>() {});
        return response;
    }

    public TrackingMoreResponse RetrackTrackingByID(String idSting) throws TrackingMoreException,IOException{
        if(StrUtils.isEmpty(idSting)){
            throw new TrackingMoreException(ErrorEnums.ErrEmptyId);
        }
        String apiPath =  "/trackings/retrack/"+idSting;
        String body = requestHelper.sendApiRequest(apiPath, "POST",null, null);
        TrackingMoreResponse response = objectMapper.readValue(body, new TypeReference<TrackingMoreResponse<Tracking>>() {});
        return response;
    }


}
