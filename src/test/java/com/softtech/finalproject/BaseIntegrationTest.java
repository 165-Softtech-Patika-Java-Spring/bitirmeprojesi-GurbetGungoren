package com.softtech.finalproject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softtech.finalproject.gen.dto.RestResponse;
import org.springframework.test.web.servlet.MvcResult;
import java.io.UnsupportedEncodingException;
public class BaseIntegrationTest {

    protected ObjectMapper objectMapper;

    protected boolean isSuccess(MvcResult result) throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
        RestResponse restResponse = getRestResponse(result);
        return isSuccess(restResponse);
    }
    protected RestResponse getRestResponse(MvcResult result) throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(result.getResponse().getContentAsString(), RestResponse.class);
    }
    private boolean isSuccess(RestResponse restResponse) {
        return restResponse.isSuccess();
    }
}
