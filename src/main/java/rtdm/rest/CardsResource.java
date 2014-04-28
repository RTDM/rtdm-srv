package rtdm.rest;

import restx.annotations.GET;
import restx.annotations.PUT;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
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

    @PermitAll
    @PUT("/dashboard/:dashboardKey/cards/:cardRef/status")
    public void updateCardStatus(String dashboardKey, String cardRef, Card.Status status) {
        Optional<Card> card = persistor.getCard(dashboardKey, cardRef);
        if (!card.isPresent()) {
            return;
        }
        card.get().setStatus(status);
        persistor.createOrUpdateCard(card.get());
    }

}
