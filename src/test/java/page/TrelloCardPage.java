package page;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import static io.restassured.RestAssured.given;

public class TrelloCardPage {
    private String cardName;
    private String boardName;

    public String getBoardId(String boardName) throws Exception {
        Response response = given()
                .queryParam("key", "c8232ef4e9d5c5e9641b4481138c7ea9")
                .queryParam("token", "986c1909807490857490f56cf697fb449d2e320983e78eee4ce111e86e816554")
                .when()
                .get("https://api.trello.com/1/members/me/boards");

        JSONArray jsonResponse = new JSONArray(response.asString());
        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject board = jsonResponse.getJSONObject(i);
            if (board.getString("name").equals(boardName)) {
                return board.getString("id");
            }
        }

        throw new Exception("Board not found");
    }
    public String getListId(String boardId, String listName) throws Exception {
        Response response = given()
                .queryParam("key", "c8232ef4e9d5c5e9641b4481138c7ea9")
                .queryParam("token", "986c1909807490857490f56cf697fb449d2e320983e78eee4ce111e86e816554")
                .when()
                .get("https://api.trello.com/1/boards/" + boardId + "/lists");

        JSONArray jsonResponse = new JSONArray(response.asString());
        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject list = jsonResponse.getJSONObject(i);
            if (list.getString("name").equals(listName)) {
                return list.getString("id");
            }
        }

        throw new Exception("List not found");
    }
    public String getCardId(String boardId, String cardName) throws Exception {
        Response response = given()
                .queryParam("key", "c8232ef4e9d5c5e9641b4481138c7ea9")
                .queryParam("token", "986c1909807490857490f56cf697fb449d2e320983e78eee4ce111e86e816554")
                .when()
                .get("https://api.trello.com/1/boards/" + boardId + "/cards");

        JSONArray jsonResponse = new JSONArray(response.asString());
        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject card = jsonResponse.getJSONObject(i);
            if (card.getString("name").equals(cardName)) {
                return card.getString("id");
            }
        }

        throw new Exception("Card not found");
    }
    public void createBoard(String boardName) throws Exception {

        //Define variables
        String API_KEY = "c8232ef4e9d5c5e9641b4481138c7ea9";
        String TOKEN = "986c1909807490857490f56cf697fb449d2e320983e78eee4ce111e86e816554";
        String BASE_URL = "https://api.trello.com/1/boards/";

        //Add query parameters
        RequestSpecification request = RestAssured.given();
        request.queryParam("name", boardName);
        request.queryParam("key", API_KEY);
        request.queryParam("token", TOKEN);
        request.header("Content-Type", "application/json");

        //Create the board
        Response response = request.post(BASE_URL);
    }
    public void createTwoCards() throws Exception {

        // Base URL for Trello API
        RestAssured.baseURI = "https://api.trello.com/1/";
        String API_KEY = "c8232ef4e9d5c5e9641b4481138c7ea9";
        String TOKEN = "986c1909807490857490f56cf697fb449d2e320983e78eee4ce111e86e816554";
        var listID = getListId(getBoardId("Test Board"),"To Do");

        //Create first card
        Response response = RestAssured.given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Card 1")
                .queryParam("idList", listID)
                .contentType(ContentType.JSON)
                .post("cards");

        //Create second card
        response = RestAssured.given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Card 2")
                .queryParam("idList", listID)
                .contentType(ContentType.JSON)
                .post("cards");
    }
    public void upgradeCard(String boardName) throws Exception {

        var boardId = getBoardId(boardName);
        var cardId1= getCardId(boardId, "Card 1");
        var cardId2= getCardId(boardId, "Card 2");

        //String[] cardIds = {cardId1, cardId2};
        //String selectedCardId = cardIds[new Random().nextInt(cardIds.length)];

        RestAssured.baseURI = "https://api.trello.com/1/cards/";
        RestAssured.basePath = cardId1;

        String API_KEY = "c8232ef4e9d5c5e9641b4481138c7ea9";
        String TOKEN = "986c1909807490857490f56cf697fb449d2e320983e78eee4ce111e86e816554";

        RestAssured.given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .body("{\"name\": \"Upgraded Card\"}")
                .when()
                .put()
                .then()
                .statusCode(200);
    }
    public void deleteCard(String boardName, String cardName) throws Exception {

        String API_KEY = "c8232ef4e9d5c5e9641b4481138c7ea9";
        String TOKEN = "986c1909807490857490f56cf697fb449d2e320983e78eee4ce111e86e816554";

        var boardId = getBoardId(boardName);
        var cardId= getCardId(boardId, cardName);

        given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/cards/" + cardId)
                .then()
                .statusCode(200);
    }
    public void deleteBoard(String boardName) throws Exception {
        String API_KEY = "c8232ef4e9d5c5e9641b4481138c7ea9";
        String TOKEN = "986c1909807490857490f56cf697fb449d2e320983e78eee4ce111e86e816554";
        var id = getBoardId(boardName);
        String BASE_URL = "https://api.trello.com/1/boards/" + id;

        Response response = RestAssured.given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete(BASE_URL)
                .then()
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        System.out.println("Status Code: " + statusCode);
    }







    public TrelloCardPage() {
        boardName = "";
        cardName = "";
    }
    public TrelloCardPage(String boardName, String cardName) {
        this.boardName = boardName;
        this.cardName = cardName;
    }

}
