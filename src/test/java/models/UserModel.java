package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class UserModel {
    String name;

    String job;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String updatedAt;
}
