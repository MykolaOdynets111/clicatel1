package testNGtest_helper;

public class PlainTestNGConfigs {

    private static final String TEST_SUITE_NAME = "Automation Health Check tests";

    private static String TEST_OUT_PUT_DIR = System.getProperty("user.dir")+"/out/aqa_reports/";

    public static String getTestOutPutDir(){
        return TEST_OUT_PUT_DIR;
    }

    public static String getTestSuiteName(){
        return TEST_SUITE_NAME;
    }

    /**
     * Method for setting up base dir where all automation test reports will be stored.
     * Note: under this directory will be one more dir automatically created (and will be
     * named according to TEST_SUITE_NAME). It will contain screenshots and .xml with test results
     * (in case of test failure this xmls will also contain error messages why the test was failed)
     * @param newDirName
     */
    public static void setTestOutPutDir(String newDirName) {
        TEST_OUT_PUT_DIR = newDirName;
    }
}
