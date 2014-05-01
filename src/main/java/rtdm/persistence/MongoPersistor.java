package rtdm.persistence;

import org.bson.types.ObjectId;
import restx.factory.Component;
import restx.jongo.JongoCollection;
import rtdm.domain.Activity;
import rtdm.domain.Card;
import rtdm.domain.Dashboard;
import events.EventBroker;

import javax.inject.Named;
import java.util.Optional;

@Component
public class MongoPersistor {

    private final JongoCollection dashboards;
    private final JongoCollection cards;
    private final JongoCollection activities;

    private final EventBroker eventBroker;

    public MongoPersistor(@Named("dashboards") final JongoCollection dashboards,
                          @Named("cards") final JongoCollection cards,
                          @Named("activities") final JongoCollection activities,
                          EventBroker eventBroker) {
        this.dashboards = dashboards;
        this.cards = cards;
        this.activities = activities;
        this.eventBroker = eventBroker;
    }

    public Iterable<Dashboard> getDashboards() {
        return dashboards.get().find().as(Dashboard.class);
    }

    public Optional<Dashboard> getDashboardByKey(String key) {
        return Optional.ofNullable(dashboards.get().findOne(new ObjectId(key)).as(Dashboard.class));
    }

    public Iterable<Card> getCards(String dashboardKey) {
        return cards.get().find("{ dashboardKey: # }", dashboardKey).as(Card.class);
    }

    public Iterable<Card> findCardsByCommitHash(String commitHash) {
        return cards.get().find("{ commits: {$elemMatch: {sha: #}} }", commitHash).as(Card.class);
    }

    public Optional<Card> getCardByRef(String dashboardKey, String cardRef) {
        return Optional.ofNullable(cards.get().findOne("{ dashboardKey: #, ref: # }", dashboardKey, cardRef).as(Card.class));
    }

    public Optional<Card> getCardByKey(String cardKey) {
        return Optional.ofNullable(cards.get().findOne(new ObjectId(cardKey)).as(Card.class));
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
        String eventType;
        if (card.getKey() == null) {
            // workaround jongo 1.0 bug - shouldn't be necessary
            card.setKey(new ObjectId().toString());
            eventType = "card.CREATED";
        } else {
            eventType = "card.UPDATED";
        }

        cards.get().save(card);
        eventBroker.broadcast(
                "dashboard." + card.getDashboardKey(),
                new CardEvent(eventType, card));
        return true;
    }

    public boolean deleteCard(String key) {
        cards.get().remove(key);
        return true;
    }

    public boolean createActivity(Activity activity) {
        activity.setKey(new ObjectId().toString());
        activities.get().save(activity);

        eventBroker.broadcast(
                "dashboard." + activity.getDashboardKey(),
                new ActivityEvent(activity));
        return true;
    }

    public Iterable<Activity> getActivities() {
        return activities.get().find().as(Activity.class);
    }

    public Iterable<Activity> finalActivitiesByDashboard(String dashboardKey) {
        return activities.get().find("{dashboardKey: #}", dashboardKey).as(Activity.class);
    }
}
