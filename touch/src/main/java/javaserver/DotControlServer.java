package javaserver;

import datamanager.jacksonschemas.dotcontrol.MessageResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DotControlServer extends Server {

    public static volatile Map<String, MessageResponse> dotControlIncomingRequests = new HashMap<>();
    public static volatile List<String> dotControlMessages = new ArrayList<>();
}
