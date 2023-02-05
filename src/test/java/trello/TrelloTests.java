package trello;

import page.TrelloCardPage;

import static io.restassured.RestAssured.given;

public class TrelloTests {
    public static void main(String[] args) throws Exception {

        TrelloCardPage trelloCardPage = new TrelloCardPage();
        var boardName = "Test Board";

        trelloCardPage.createBoard(boardName);
        trelloCardPage.createTwoCards();
        trelloCardPage.upgradeCard(boardName);
        trelloCardPage.deleteCard(boardName,"Upgraded Card");
        trelloCardPage.deleteCard(boardName,"Card 2");
        trelloCardPage.deleteBoard(boardName);

    }
}