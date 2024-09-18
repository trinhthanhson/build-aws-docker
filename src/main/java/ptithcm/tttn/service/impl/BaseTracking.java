package ptithcm.tttn.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import ptithcm.tttn.request.RequestHelper;

public class BaseTracking {
    protected RequestHelper requestHelper;

    protected ObjectMapper objectMapper;
    public BaseTracking(){
        this.requestHelper = new RequestHelper();
        this.objectMapper = new ObjectMapper();
    }
}
