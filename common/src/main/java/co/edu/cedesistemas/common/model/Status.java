package co.edu.cedesistemas.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Status<T> {
    public static final Integer SUCCESS_CODE = 0;
    public static String SUCCESS_MESSAGE = "success";

    private Integer code;
    private String message;
    private Integer _hits;

    private T _source;
    private String _class;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> details;

    public static Status<?> success() {
        return Status.builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                ._class(Status.class.getName())
                .build();
    }

    public static Status<?> fromJson(final String json) {
        Gson gson = new GsonBuilder()
                .create();
        return gson.fromJson(json, Status.class);
    }
}
