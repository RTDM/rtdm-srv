package rtdm.persistence;

import restx.factory.Component;
import restx.jongo.JongoCollection;
import rtdm.domain.Card;
import rtdm.domain.Dashboard;

import javax.inject.Named;
import java.util.Optional;

@Component
public class MongoPersistor {

    private final JongoCollection dashboards;
    private final JongoCollection cards;

    public MongoPersistor(@Named("dashboards") JongoCollection dashboards,
                          @Named("cards") JongoCollection cards) {
        this.dashboards = dashboards;
        this.cards = cards;
    }

    public Iterable<Dashboard> getDashboards() {
        return dashboards.get().find().as(Dashboard.class);
    }

    public Optional<Dashboard> getDashboard(String name) {
        return Optional.ofNullable(dashboards.get().findOne("{ name: # }", name).as(Dashboard.class));
    }

    public Iterable<Card> getCards(String dashboardKey) {
        return cards.get().find("{ dashboardKey: # }", dashboardKey).as(Card.class);
    }

    public Optional<Card> getCard(String dashboardKey, String cardRef) {
        return Optional.ofNullable(cards.get().findOne("{ dashboardKey: #, ref: # }", dashboardKey, cardRef).as(Card.class));
    }

    public boolean createOrUpdateCard(Card card) {
        cards.get().save(card);
        return true;
    }

    public boolean deleteCard(String key) {
        cards.get().remove(key);
        return true;
    }
}
