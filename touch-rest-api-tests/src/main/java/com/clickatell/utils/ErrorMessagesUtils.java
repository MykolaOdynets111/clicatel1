package com.clickatell.utils;

import com.clickatell.models.ErrorMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by oshchur on 18.07.2016.
 */
public class ErrorMessagesUtils {
    public static boolean checkErrorMessage(String message, List<ErrorMessage> errorMessagesList) {
        return checkErrorMessage(new String[]{message}, errorMessagesList);
    }

    public static boolean checkErrorMessage(String message, ErrorMessage[] errorMessagesList) {
        try {
            return checkErrorMessage(new String[]{message}, Arrays.asList(errorMessagesList));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean checkErrorMessage(String[] messageList, List<ErrorMessage> errorMessagesList) {
        ArrayList<String> errorsStringsList = new ArrayList<>();
        for (ErrorMessage error : errorMessagesList) {
            errorsStringsList.add(error.getErrorMessage());
        }
        return errorsStringsList.containsAll(Arrays.asList(messageList));
    }
}
