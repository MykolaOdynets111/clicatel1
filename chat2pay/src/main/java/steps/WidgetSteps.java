package steps;

import api.ApiHelperChat2Pay;
import api.ApiHelperWidgets;
import data.models.request.WidgetBody;
import data.models.response.c2pconfiguration.SupportedCurrency;
import data.models.response.pageable.Pageable;
import data.models.response.updatedresponse.UpdatedEntityResponse;
import data.models.response.widget.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static api.ApiHelperAccounts.getAccountSettingsResponse;
import static api.ApiHelperApiKeysManagement.getApiKeysManagement;
import static api.ApiHelperWidgets.getWidgetsContent;
import static data.enums.WidgetsInfo.GENERAL_WIDGET_NAME;
import static datetimeutils.DateTimeHelper.parseToLocalDate;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.checkResponseCode;
import static utils.Validator.validateErrorResponse;

public class WidgetSteps extends GeneralSteps {

    private Response response;

    @When("^User gets widgetId for (.*) form$")
    public void getWidgetId(String widgetName) {
        setWidgetId(widgetName);
    }

    @When("^User gets application Id for widget$")
    public void getApplicationId() {
        setApplicationId();
    }

    @When("^User gets activation key for widget$")
    public void getActivationKey() {
        setActivationKey();
    }

    @Then("^User creates new widget")
    public void createWidget() {
        createNewWidget();
    }

    @Then("^User sets up 'show_linked_api'")
    public void setUpShowLinkedApi() {
        Widget body = Widget.builder().showLinkedApi(true).build();
        response = ApiHelperWidgets.updateShowLinkedApiForWidget(createdWidgetId.get(), body);
        checkResponseCode(response, 200);
    }

    @Then("^User creates widget for an account$")
    public void createWidget(Map<String, String> dataMap) {
        WidgetBody widget = WidgetBody.builder()
                .type(dataMap.get("i.type"))
                .environment(dataMap.get("i.environment"))
                .build();
        response = ApiHelperWidgets.createWidget(widget);

        checkResponseCode(response, getExpectedCode(dataMap));
        String status = dataMap.get("i.widget");
        switch (status) {
            case "valid":
                WidgetCreation widgetCreation = response.as(WidgetCreation.class);
                softly.assertThat(widgetCreation.getWidgetId())
                        .as("widget ID is not exist in the response")
                        .isNotEqualTo(null);
                softly.assertThat(widgetCreation.getCreatedTime())
                        .as(format("widget creation date is not equals to %s", LocalDate.now()))
                        .isEqualTo(LocalDate.now());
                softly.assertAll();
                createdWidgetId.set(response.as(WidgetCreation.class).getWidgetId());
                break;
            case "nonexisted":
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", status));
        }
    }

    @Then("^User gets newly created widget$")
    public void getCreatedWidget(Map<String, String> dataMap) {
        switch (getWidgetId(dataMap)) {
            case "valid":
                response = ApiHelperWidgets.getWidget(createdWidgetId.get());
                checkResponseCode(response, getExpectedCode(dataMap));

                Widget widget = response.as(Widget.class);
                softly.assertThat(widget.getModifiedTime()).isNotNull();
                softly.assertThat(widget.getCreatedTime()).isNotNull();
                softly.assertThat(createdWidgetName.get()).isEqualTo(widget.getName());
                softly.assertThat(dataMap.get("o.status")).isEqualTo(widget.getStatus());
                softly.assertThat(dataMap.get("o.environment")).isEqualTo(widget.getEnvironment());
                softly.assertAll();
                break;
            case "non_existed":
                response = ApiHelperWidgets.getWidget(getWidgetId(dataMap));
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    @Then("^User updates newly created widget$")
    public void updateCreatedWidget(Map<String, String> dataMap) {
        createdWidgetName.set(generateWidgetName());
        Widget updateBody = Widget.builder()
                .name(createdWidgetName.get())
                .environment(dataMap.get("i.environment"))
                .status(dataMap.get("i.status"))
                .configStatus(ConfigStatus.builder()
                        .id(Integer.parseInt(dataMap.get("i.configStatus_id")))
                        .name(dataMap.get("i.configStatus_name")).build())
                .build();

        switch (getWidgetId(dataMap)) {
            case "valid":
                response = ApiHelperWidgets.updateWidget(createdWidgetId.get(), updateBody);
                checkResponseCode(response, getExpectedCode(dataMap));
                assertThat(response.as(UpdatedEntityResponse.class).getUpdateTime())
                        .as(format("widget update date is not equals to %s", LocalDate.now()))
                        .isEqualTo(LocalDate.now());
                break;
            case "non_existed":
                response = ApiHelperWidgets.updateWidget(getWidgetId(dataMap), updateBody);
                validateErrorResponse(response, dataMap);
                break;
            case "wrong_status":
            case "wrong_env":
                response = ApiHelperWidgets.updateWidget(createdWidgetId.get(), updateBody);
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    @Then("^User updates show_linked_api for newly created widget$")
    public void updateShowLinkedApiCreatedWidget(Map<String, String> dataMap) {
        Widget body = Widget.builder().showLinkedApi(Boolean.parseBoolean(dataMap.get("i.showLinkedApi"))).build();
        switch (getWidgetId(dataMap)) {
            case "valid":
                response = ApiHelperWidgets.updateShowLinkedApiForWidget(createdWidgetId.get(), body);
                checkResponseCode(response, getExpectedCode(dataMap));
                UpdatedEntityResponse updatedEntityResponse = response.as(UpdatedEntityResponse.class);
                softly.assertThat(updatedEntityResponse.getUpdateTime())
                        .as(format("widget update date is not equals to %s", LocalDate.now()))
                        .isEqualTo(LocalDate.now());
                softly.assertThat(updatedEntityResponse.isShowLinkedApi())
                        .isEqualTo(Boolean.valueOf(Boolean.parseBoolean(dataMap.get("i.showLinkedApi"))));
                softly.assertThat(ApiHelperWidgets.getWidget(createdWidgetId.get()).as(Widget.class)
                                .isShowLinkedApi())
                        .isEqualTo(Boolean.valueOf(Boolean.parseBoolean(dataMap.get("i.showLinkedApi"))));
                softly.assertAll();
                break;
            case "non_existed":
                response = ApiHelperWidgets.updateShowLinkedApiForWidget(getWidgetId(dataMap), body);
                checkResponseCode(response, getExpectedCode(dataMap));
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    @Then("^User delete widget$")
    public void deleteWidget(Map<String, String> dataMap) {
        switch (getWidgetId(dataMap)) {
            case "valid":
                response = ApiHelperWidgets.deleteWidget(createdWidgetId.get());
                checkResponseCode(response, getExpectedCode(dataMap));
                UpdatedEntityResponse updatedEntityResponse = response.as(UpdatedEntityResponse.class);
                softly.assertThat(updatedEntityResponse.getUpdateTime())
                        .as(format("widget update date is not equals to %s", LocalDate.now()))
                        .isEqualTo(LocalDate.now());
                break;
            case "non_existed":
                response = ApiHelperWidgets.deleteWidget(getWidgetId(dataMap));
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    private void createNewWidget() {
        response = ApiHelperWidgets.createWidget(new WidgetBody());
        if (response.statusCode() != 200) {
            Assert.fail("Could not create widget! Error code: " + response.statusCode());
        } else {
            createdWidgetId.set(response.as(WidgetCreation.class).getWidgetId());
            Widget updateBody = Widget.builder()
                    .name(generateWidgetName())
                    .status("CONFIGURED")
                    .configStatus(new ConfigStatus())
                    .build();
            Response putQuery = ApiHelperWidgets.updateWidget(createdWidgetId.get(), updateBody);

            if (putQuery.getStatusCode() != 200) {
                Assert.fail(format("Could not update widget! Error code: %s. Error message: %s",
                        putQuery.getStatusCode(),
                        putQuery.body().jsonPath().get()));
            }
        }
    }

    @Then("^User get the widget configuration")
    public void getWidgetConfiguration(Map<String, String> dataMap) {
        switch (dataMap.get("i.widgetId")) {
            case "valid":
                WidgetBody widgetBody = new WidgetBody();
                String accountId = getAccountSettingsResponse(getActivationKey(dataMap)).jsonPath().getString("accountId");
                List<Widget> widgets = getWidgetsContent().getWidgets();
                Widget widget = widgets.stream().filter(w -> w.getId().equals(createdWidgetId.get())).findFirst()
                        .orElseThrow(() -> new NoSuchElementException("There is no such widget: " + createdWidgetId.get()));
                String apiKey = getApiKeysManagement(createdWidgetId.get(), ApiHelperChat2Pay.token.get()).jsonPath().getList("apiKey").get(0).toString();

                softly.assertThat(widget.getName()).startsWith(GENERAL_WIDGET_NAME.name);
                softly.assertThat(widget.getApiKey()).isEqualToIgnoringCase(apiKey);
                softly.assertThat(widget.getStatus()).isEqualToIgnoringCase(dataMap.get("o.status"));
                softly.assertThat(widget.getType()).isEqualTo(widgetBody.getType());
                softly.assertThat(widget.getEnvironment()).isEqualTo(widgetBody.getEnvironment());
                softly.assertThat(widget.getConfigStatus().getId()).isEqualTo(widgetBody.getConfigStatus().getId());
                softly.assertThat(widget.getConfigStatus().getName()).isEqualToIgnoringCase(widgetBody.getConfigStatus().getName());
                softly.assertThat(widget.getAccountId()).isEqualTo(accountId);
                softly.assertThat(parseToLocalDate(widget.getCreatedTime())).isToday();
                softly.assertThat(parseToLocalDate(widget.getModifiedTime())).isToday();

                SupportedCurrency currency = widget.getSupportedCurrencies().stream().findAny()
                        .orElseThrow(() -> new NoSuchElementException("Support currency is absent"));
                softly.assertThat(currency.getId()).isEqualTo(156);
                softly.assertThat(currency.getIso()).isEqualTo("ZAR");
                softly.assertThat(currency.getName()).isEqualToIgnoringCase(dataMap.get("o.defaultCurrency"));
                softly.assertThat(currency.getSymbol()).isEqualTo("R");
                softly.assertThat(currency.isDefault).isEqualTo(true);

                WidgetIntegrator integrator = widget.getIntegrators().stream().findAny()
                        .orElseThrow(() -> new NoSuchElementException("Integration is absent"));
                softly.assertThat(integrator.getName()).startsWith(GENERAL_WIDGET_NAME.name);
                softly.assertThat(integrator.getApplicationId()).isNotNull();
                softly.assertThat(integrator.getType()).isEqualTo("APPLICATION");
                softly.assertThat(integrator.getStatus()).isEqualTo("ACTIVATED");

                WidgetsContent widgetsContent = getWidgetsContent();
                Pageable pageable = getWidgetsContent().getPageable();
                softly.assertThat(pageable.getSort().getUnsorted()).isTrue();
                softly.assertThat(pageable.getSort().getSorted()).isFalse();
                softly.assertThat(pageable.getSort().getEmpty()).isTrue();

                softly.assertThat(pageable.getPageNumber()).isEqualTo(0);
                softly.assertThat(pageable.getPageSize()).isEqualTo(200);
                softly.assertThat(pageable.getOffset()).isEqualTo(0);
                softly.assertThat(pageable.getUnpaged()).isFalse();
                softly.assertThat(pageable.getPaged()).isTrue();

                softly.assertThat(widgetsContent.isLast()).isTrue();
                softly.assertThat(widgetsContent.getTotalPages()).isEqualTo(1);
                softly.assertThat(widgetsContent.getTotalElements()).isEqualTo(widgets.size());
                softly.assertThat(widgetsContent.getSort().getUnsorted()).isTrue();
                softly.assertThat(widgetsContent.getSort().getSorted()).isFalse();
                softly.assertThat(widgetsContent.getSort().getEmpty()).isTrue();

                softly.assertThat(widgetsContent.isFirst()).isTrue();
                softly.assertThat(widgetsContent.getNumber()).isEqualTo(0);
                softly.assertThat(widgetsContent.getNumberOfElements()).isEqualTo(widgets.size());
                softly.assertThat(widgetsContent.getSize()).isEqualTo(200);
                softly.assertThat(widgetsContent.isEmpty()).isFalse();

                softly.assertAll();
                break;
            case "invalid":
                softly.assertThat(parseInt(dataMap.get("o.responseCode"))).isEqualTo(404);
                softly.assertThat(dataMap.get("o.errors")).isEqualTo("NOT_FOUND");
                softly.assertAll();
                break;
            default:
                Assertions.fail(format("The response code is not as expected %s", getExpectedCode(dataMap)));
        }
    }

    @NotNull
    private String generateWidgetName() {
        String name = format("%s - %s", GENERAL_WIDGET_NAME.name, faker.funnyName().name());
        return name.length() > 32 ? name.substring(0, 31) : name;
    }
}