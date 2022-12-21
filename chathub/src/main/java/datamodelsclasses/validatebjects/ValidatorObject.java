package datamodelsclasses.validatebjects;

import lombok.Getter;

@Getter
public abstract class ValidatorObject {

    int responseCode;

    public ValidatorObject(Object obj){
        if (obj instanceof Integer){
                this.responseCode = (Integer) obj;
        } else{
            throw new AssertionError("Incorrect parameter for Status code was provided");
        }
    }

}
