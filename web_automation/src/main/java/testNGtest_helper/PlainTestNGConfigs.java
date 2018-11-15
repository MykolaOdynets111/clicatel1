package testNGtest_helper;

public class PlainTestNGConfigs {

    private static final String TEST_SUITE_NAME = "Automation Health Check tests";

    private static final String TEST_OUT_PUT_DIR = System.getProperty("user.dir")+"/out/aqa_reports/";

    public static String getTestOutPutDir(){
        return TEST_OUT_PUT_DIR;
    }

    public static final String getTestSuiteName(){
        return TEST_SUITE_NAME;
    }
}
