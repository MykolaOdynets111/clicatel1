package apihelper;

import datamanager.Tenants;
import datamanager.jacksonschemas.Tag;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class APIHelperTags extends ApiHelper {

    public static List<String> getTagsForCRMTicket(String chatId) {
        return RestAssured.given().header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(format(Endpoints.CHATS_INFO, chatId)).getBody().jsonPath().getList("tags.value");
    }

    public static List<String> getAllTagsTitle() {
        return getAllTags().stream().map(Tag::getValue).collect(Collectors.toList());
    }

    public static void setTagsStatus(String tagName, String status) {
        Tag tag = getTagByName(tagName);
        tag.setEnabled(status);

        Response resp = RestAssured.given()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .contentType(ContentType.JSON)
                .body(tag)
                .put(Endpoints.TAGS_FOR_CRM_TICKET + "/" + tag.getId());
        if (resp.getStatusCode() != 200) {
            Assert.fail("Update Tag failed \n"
                    + "Status code: " + resp.statusCode() + "\n"
                    + "Error message: " + resp.getBody().asString());
        }
    }

    private static Tag getTagByName(String name) {
        return getAllTags().stream().filter(t -> t.getValue().equals(name))
                .findFirst().orElseThrow(() -> new NoSuchElementException(name + " tag is absent!"));
    }

    private static List<Tag> getAllTags() {
        return getTouchQuery(Tenants.getTenantUnderTestOrgName(), Endpoints.TAGS_FOR_CRM_TICKET)
                .jsonPath().getList("", Tag.class);
    }
}
