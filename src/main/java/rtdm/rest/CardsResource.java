package rtdm.rest;

import org.joda.time.DateTime;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.PUT;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import rtdm.domain.Activity;
import rtdm.domain.Activity.Event;
import rtdm.domain.Card;
import rtdm.persistence.MongoPersistor;

import java.util.Optional;

import static restx.common.MorePreconditions.checkEquals;

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

    @PermitAll
    @GET("/dashboard/:dashboardKey/cards/byRef/:cardRef")
    public Optional<Card> findCardByRef(String dashboardKey, String cardRef) {
        return persistor.getCardByRef(dashboardKey, cardRef);
    }

    public Iterable<Card> findCardByCommitHash(String commitHash) {
        return persistor.findCardsByCommitHash(commitHash);
    }

    @POST("/dashboard/:dashboardKey/cards")
    public Iterable<Card> addCard(String dashboardKey, Card card) {
        if (card.getKey() != null) {
            throw new IllegalArgumentException("can't create Card which already has a key: " + card);
        }
        if (card.getDashboardKey() != null) {
            checkEquals(":dashboardKey", dashboardKey, "card.dashboardKey", card.getDashboardKey());
        } else {
            card.setDashboardKey(dashboardKey);
        }
        card.setStatus(Card.Status.TODO);
        persistor.createOrUpdateCard(dashboardKey, card);
        persistor.createActivity(
                new Activity()
                        .setDashboardKey(card.getDashboardKey())
                        .setCard(card)
                        .setEvent(Activity.Event.CARD_CREATED)
                        .setTimestamp(DateTime.now())
        );
        return persistor.getCards(dashboardKey);
    }

    @PUT("/dashboard/:dashboardKey/cards/:cardKey")
    public void updateCard(String dashboardKey, String cardKey, Card card) {
        checkEquals(":cardKey", cardKey, "card.key", card.getKey());
        if (card.getDashboardKey() != null) {
            checkEquals(":dashboardKey", dashboardKey, "card.dashboardKey", card.getDashboardKey());
        } else {
            card.setDashboardKey(dashboardKey);
        }
        persistor.createOrUpdateCard(dashboardKey, card);
        persistor.createActivity(
                new Activity()
                        .setDashboardKey(card.getDashboardKey())
                        .setCard(card)
                        .setEvent(Event.CARD_UPDATED)
                        .setTimestamp(DateTime.now())
        );
    }

    @PUT("/dashboard/:dashboardKey/cards/:cardKey/:status")
    public Optional<Card> updateCardStatus(String dashboardKey, String cardKey, Card.Status status) {
        Optional<Card> card = persistor.getCardByKey(cardKey);
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
