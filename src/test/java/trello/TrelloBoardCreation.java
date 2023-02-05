package trello;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TrelloBoardCreation {
    public static void main(String[] args) {
        // Set up the environment variables for your Trello API key and token
        RestAssured.baseURI = "https://api.trello.com/1/";
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        // Define the parameters for creating the Trello board
        request.queryParam("name", "Test Board");
        request.queryParam("key", "key");
        request.queryParam("token", "token");

        // Send the POST request to the Trello API endpoint for creating boards
        Response response = request.post("boards/");

        // Parse the response from Trello to confirm the board was created successfully
        int statusCode = response.getStatusCode();
        if (statusCode == 200) {
            System.out.println("Board created successfully!");
        } else {
            System.out.println("Error creating board: " + response.getBody().asString());
        }
    }
}
