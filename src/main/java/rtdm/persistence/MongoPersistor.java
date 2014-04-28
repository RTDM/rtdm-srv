package rtdm.persistence;

import org.bson.types.ObjectId;
import restx.factory.Component;
import restx.jongo.JongoCollection;
import rtdm.domain.Activity;
import rtdm.domain.Card;
import rtdm.domain.Dashboard;

import javax.inject.Named;
import java.util.Optional;

@Component
public class MongoPersistor {

    private final JongoCollection dashboards;
    private final JongoCollection cards;
    private final JongoCollection activities;

    public MongoPersistor(@Named("dashboards") final JongoCollection dashboards,
                          @Named("cards") final JongoCollection cards,
                          @Named("activities") final JongoCollection activities) {
        this.dashboards = dashboards;
        this.cards = cards;
        this.activities = activities;
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

    public boolean createOrUpdateDashboard(Dashboard dashboard) {
        dashboard.setKey(new ObjectId().toString());
        dashboards.get().save(dashboard);
        return true;
    }

    public boolean deleteDashboard(String key) {
        dashboards.get().remove(key);
        return true;
    }

    public boolean createOrUpdateCard(String dashboardKey, Card card) {
        card.setDashboardKey(dashboardKey);
        card.setKey(new ObjectId().toString());
        cards.get().save(card);
        return true;
    }

    public boolean deleteCard(String key) {
        cards.get().remove(key);
        return true;
    }

    public boolean createActivity(Activity activity) {
        activity.setKey(new ObjectId().toString());
        activities.get().save(activity);
        return true;
    }
}
