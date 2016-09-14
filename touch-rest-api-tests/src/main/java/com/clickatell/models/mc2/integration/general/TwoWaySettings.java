
package com.clickatell.models.mc2.integration.general;

import com.clickatell.utils.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class TwoWaySettings {

    private String id;
    private String httpMethod = HttpMethod.GET_METHOD;
    private String callbackUrl = "http://localhost";
    private Boolean httpAuthHeaderEnabled = false;
    private String callbackUser = "User_" + StringUtils.generateRandomString(10);
    private String callbackPswd = "Pass_" + StringUtils.generateRandomString(10);
    private Boolean predefinedCommandsEnabled;
    private List<CmdOptOut> cmdOptOut = new ArrayList<CmdOptOut>();
    private CmdOptIn cmdOptIn;
    private CmdHelp cmdHelp;
    private Boolean customCommandsEnabled = false;
    private List<CmdCustom> cmdCustom = new ArrayList<CmdCustom>();
    private String noTwoWayAction = NoTwoWaySupport.SEND_ONE_WAY.name();
    //TODO add tests to cover this field
    private Boolean enabled = false;

    /**
     * No args constructor for use in serialization
     */
    public TwoWaySettings() {
        cmdOptOut = createDefaultCmdOptOutValues();
        cmdOptIn = new CmdOptIn();
        cmdHelp = new CmdHelp();
    }

    /**
     * @param cmdCustom
     * @param id
     * @param noTwoWayAction
     * @param callbackUser
     * @param callbackUrl
     * @param cmdOptIn
     * @param httpAuthHeaderEnabled
     * @param cmdHelp
     * @param callbackPswd
     * @param httpMethod
     * @param customCommandsEnabled
     * @param cmdOptOut
     * @param predefinedCommandsEnabled
     */
    public TwoWaySettings(String id, String httpMethod, String callbackUrl, Boolean httpAuthHeaderEnabled, String callbackUser, String callbackPswd, Boolean predefinedCommandsEnabled, List<CmdOptOut> cmdOptOut, CmdOptIn cmdOptIn, CmdHelp cmdHelp, Boolean customCommandsEnabled, List<CmdCustom> cmdCustom, String noTwoWayAction) {
        this.id = id;
        this.httpMethod = httpMethod;
        this.callbackUrl = callbackUrl;
        this.httpAuthHeaderEnabled = httpAuthHeaderEnabled;
        this.callbackUser = callbackUser;
        this.callbackPswd = callbackPswd;
        this.predefinedCommandsEnabled = predefinedCommandsEnabled;
        this.cmdOptOut = cmdOptOut;
        this.cmdOptIn = cmdOptIn;
        this.cmdHelp = cmdHelp;
        this.customCommandsEnabled = customCommandsEnabled;
        this.cmdCustom = cmdCustom;
        this.noTwoWayAction = noTwoWayAction;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public TwoWaySettings withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The httpMethod
     */
    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     * @param httpMethod The httpMethod
     */
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public TwoWaySettings withHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    /**
     * @return The callbackUrl
     */
    public String getCallbackUrl() {
        return callbackUrl;
    }

    /**
     * @param callbackUrl The callbackUrl
     */
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public TwoWaySettings withCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    /**
     * @return The httpAuthHeaderEnabled
     */
    public Boolean getHttpAuthHeaderEnabled() {
        return httpAuthHeaderEnabled;
    }

    /**
     * @param httpAuthHeaderEnabled The httpAuthHeaderEnabled
     */
    public void setHttpAuthHeaderEnabled(Boolean httpAuthHeaderEnabled) {
        this.httpAuthHeaderEnabled = httpAuthHeaderEnabled;
    }

    public TwoWaySettings withHttpAuthHeaderEnabled(Boolean httpAuthHeaderEnabled) {
        this.httpAuthHeaderEnabled = httpAuthHeaderEnabled;
        return this;
    }

    /**
     * @return The callbackUser
     */
    public String getCallbackUser() {
        return callbackUser;
    }

    /**
     * @param callbackUser The callbackUser
     */
    public void setCallbackUser(String callbackUser) {
        this.callbackUser = callbackUser;
    }

    public TwoWaySettings withCallbackUser(String callbackUser) {
        this.callbackUser = callbackUser;
        return this;
    }

    /**
     * @return The callbackPswd
     */
    public String getCallbackPswd() {
        return callbackPswd;
    }

    /**
     * @param callbackPswd The callbackPswd
     */
    public void setCallbackPswd(String callbackPswd) {
        this.callbackPswd = callbackPswd;
    }

    public TwoWaySettings withCallbackPswd(String callbackPswd) {
        this.callbackPswd = callbackPswd;
        return this;
    }

    /**
     * @return The predefinedCommandsEnabled
     */
    public Boolean getPredefinedCommandsEnabled() {
        return predefinedCommandsEnabled;
    }

    /**
     * @param predefinedCommandsEnabled The predefinedCommandsEnabled
     */
    public void setPredefinedCommandsEnabled(Boolean predefinedCommandsEnabled) {
        this.predefinedCommandsEnabled = predefinedCommandsEnabled;
    }

    public TwoWaySettings withPredefinedCommandsEnabled(Boolean predefinedCommandsEnabled) {
        this.predefinedCommandsEnabled = predefinedCommandsEnabled;
        return this;
    }

    /**
     * @return The cmdOptOut
     */
    public List<CmdOptOut> getCmdOptOut() {
        return cmdOptOut;
    }

    /**
     * @param cmdOptOut The cmdOptOut
     */
    public void setCmdOptOut(List<CmdOptOut> cmdOptOut) {
        this.cmdOptOut = cmdOptOut;
    }

    public TwoWaySettings withCmdOptOut(List<CmdOptOut> cmdOptOut) {
        this.cmdOptOut = cmdOptOut;
        return this;
    }

    /**
     * @return The cmdOptIn
     */
    public CmdOptIn getCmdOptIn() {
        return cmdOptIn;
    }

    /**
     * @param cmdOptIn The cmdOptIn
     */
    public void setCmdOptIn(CmdOptIn cmdOptIn) {
        this.cmdOptIn = cmdOptIn;
    }

    public TwoWaySettings withCmdOptIn(CmdOptIn cmdOptIn) {
        this.cmdOptIn = cmdOptIn;
        return this;
    }

    /**
     * @return The cmdHelp
     */
    public CmdHelp getCmdHelp() {
        return cmdHelp;
    }

    /**
     * @param cmdHelp The cmdHelp
     */
    public void setCmdHelp(CmdHelp cmdHelp) {
        this.cmdHelp = cmdHelp;
    }

    public TwoWaySettings withCmdHelp(CmdHelp cmdHelp) {
        this.cmdHelp = cmdHelp;
        return this;
    }

    /**
     * @return The customCommandsEnabled
     */
    public Boolean getCustomCommandsEnabled() {
        return customCommandsEnabled;
    }

    /**
     * @param customCommandsEnabled The customCommandsEnabled
     */
    public void setCustomCommandsEnabled(Boolean customCommandsEnabled) {
        this.customCommandsEnabled = customCommandsEnabled;
    }

    public TwoWaySettings withCustomCommandsEnabled(Boolean customCommandsEnabled) {
        this.customCommandsEnabled = customCommandsEnabled;
        return this;
    }

    /**
     * @return The cmdCustom
     */
    public List<CmdCustom> getCmdCustom() {
        return cmdCustom;
    }

    /**
     * @param cmdCustom The cmdCustom
     */
    public void setCmdCustom(List<CmdCustom> cmdCustom) {
        this.cmdCustom = cmdCustom;
    }

    public TwoWaySettings withCmdCustom(List<CmdCustom> cmdCustom) {
        this.cmdCustom = cmdCustom;
        return this;
    }

    /**
     * @return The noTwoWayAction
     */
    public String getNoTwoWayAction() {
        return noTwoWayAction;
    }

    /**
     * @param noTwoWayAction The noTwoWayAction
     */
    public void setNoTwoWayAction(String noTwoWayAction) {
        this.noTwoWayAction = noTwoWayAction;
    }

    public TwoWaySettings withNoTwoWayAction(String noTwoWayAction) {
        this.noTwoWayAction = noTwoWayAction;
        return this;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public TwoWaySettings withEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private ArrayList<CmdOptOut> createDefaultCmdOptOutValues() {
        ArrayList<CmdOptOut> result = new ArrayList<>();
        for (OptOutKeyWords keyWord : OptOutKeyWords.values()) {
            result.add(new CmdOptOut(keyWord.name()));
        }
        return result;
    }

}
