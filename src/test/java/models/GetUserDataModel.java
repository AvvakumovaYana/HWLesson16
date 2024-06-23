package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetUserDataModel {
    ResponseData data;
    SupportData support;

    @Data
    public static class ResponseData {
        Integer id;
        String name;
        Integer year;
        String color;

        @JsonProperty("pantone_value")
        String pantoneValue;
    }

    @Data
    public static class SupportData {
        String url;
        String text;
    }
}
