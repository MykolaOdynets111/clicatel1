package api.validatebjects;

import lombok.Getter;

import java.util.Map;

@Getter
public class ErrorValidatorObject extends ValidatorObject{

    String errorMessage;

    public ErrorValidatorObject(Map<String, String> data) {
        super(Integer.parseInt(data.get("o.responseCode")));
        this.errorMessage = data.get("o.errorMessage");
    }
}
