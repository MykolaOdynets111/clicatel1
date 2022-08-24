package testflo.jacksonschemas;


import java.util.List;
import java.util.Map;

public interface AllureScenarioInterface {

    String getTestId();

    List<Map<String, String>> getStepsWithStatuses();

    String getName();

    String getDescription();

    String getFailureMessage();

    String getStatus();
}
