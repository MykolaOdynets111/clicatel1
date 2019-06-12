package testflo.jacksonschemas;

import testflo.jacksonschemas.localallurereport.Step;

import java.util.List;
import java.util.Map;

public interface AllureScenarioInterface {

    public String getTestId();

    public List<Map<String, String>> getStepsWithStatuses();

    public String getName();

    public String getDescription();

    public String getFailureMessage();

    public String getStatus();
}
