package rtdm.rest;

import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.jongo.JongoCollection;
import restx.security.PermitAll;
import rtdm.domain.Card;

import javax.inject.Named;

/**
 * Date: 27/4/14
 * Time: 20:38
 */
@RestxResource @Component
public class CardsResource {
    private final JongoCollection cards;

    public CardsResource(@Named("cards") JongoCollection cards) {
        this.cards = cards;
    }

    @PermitAll
    @GET("/dashboard/:dashboardKey/cards")
    public Iterable<Card> findCardsForDashBoard(String dashboardKey) {
        return cards.get().find("{dashboardRef: #}", dashboardKey).as(Card.class);
    }

}
