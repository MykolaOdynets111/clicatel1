package api.validatebjects;

import lombok.Getter;

@Getter
public abstract class ValidatorObject {

    int responseCode;

    public ValidatorObject(String responseCode){
        this.responseCode = Integer.parseInt(responseCode);
        }

}
