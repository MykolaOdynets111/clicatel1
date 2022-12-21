package datamodelsclasses.validatebjects;

import lombok.Getter;

import java.util.Map;

@Getter
public class ErrorValidatorObject extends ValidatorObject{

    String errorMessage;

    public ErrorValidatorObject(Map<String, String> data) {
        super(data.get("responseCode"));
        this.errorMessage = data.get("errorMessage");
    }
}
