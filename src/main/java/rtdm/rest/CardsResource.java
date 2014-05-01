package rtdm.rest;

import org.joda.time.DateTime;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.PUT;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import rtdm.domain.Activity;
import rtdm.domain.Card;
import rtdm.persistence.MongoPersistor;

import java.util.Optional;

/**
 * Date: 27/4/14
 * Time: 20:38
 */
@RestxResource
@Component
public class CardsResource {

    private final MongoPersistor persistor;

    public CardsResource(MongoPersistor persistor) {
        this.persistor = persistor;
    }

    @PermitAll
    @GET("/dashboard/:dashboardKey/cards")
    public Iterable<Card> findCardsForDashBoard(String dashboardKey) {
        return persistor.getCards(dashboardKey);
    }

    @POST("/dashboard/:dashboardKey/cards")
    public Iterable<Card> addCard(String dashboardKey, Card card) {
        card.setStatus(Card.Status.TODO);
        card.setDashboardKey(dashboardKey);
        persistor.createOrUpdateCard(dashboardKey, card);
        persistor.createActivity(
                new Activity()
                        .setCard(card)
                        .setEvent(Activity.Event.CARD_CREATED)
                        .setTimestamp(DateTime.now())
        );
        return persistor.getCards(dashboardKey);
    }

    @PermitAll
    @PUT("/dashboard/:dashboardKey/cards/:cardRef/:status")
    public Optional<Card> updateCardStatus(String dashboardKey, String cardRef, Card.Status status) {
        Optional<Card> card = persistor.getCard(dashboardKey, cardRef);
        if (!card.isPresent()) {
            return Optional.empty();
        }
        card.get().setStatus(status);
        persistor.createOrUpdateCard(dashboardKey, card.get());
        persistor.createActivity(
                new Activity()
                        .setDashboardKey(card.get().getDashboardKey())
                        .setCard(card.get())
                        .setEvent(Activity.Event.CARD_UPDATED)
                        .setTimestamp(DateTime.now())
        );
        return card;
    }

}
