package dataManager;

import api_helper.ApiHelperTie;
import dataManager.jackson_schemas.Intent;

import java.util.List;

public class Intents {

    public static Intent getIntentWithMaxConfidence(String userMessage){
        List<Intent> listOfIntentsFromTIE = ApiHelperTie.getListOfIntentsOnUserMessage(userMessage);
        double maxConfidence = 0.00;
        for(int i =0; i<listOfIntentsFromTIE.size(); i++){
            double currentConfidence = listOfIntentsFromTIE.get(i).getConfidence();
            if(currentConfidence>maxConfidence){
                maxConfidence=currentConfidence;
            }
        }
        double conf = maxConfidence;
        return listOfIntentsFromTIE.stream().filter(e -> e.getConfidence()==conf).findFirst().get();
    }
}
