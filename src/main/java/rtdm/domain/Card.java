package rtdm.domain;

import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

/**
 * Date: 27/4/14
 * Time: 20:37
 */
public class Card {
    @Id @ObjectId
    private String key;

    private String dashboardRef;

    private String text;

    public String getKey() {
        return key;
    }

    public String getDashboardRef() {
        return dashboardRef;
    }

    public String getText() {
        return text;
    }

    public Card setKey(final String key) {
        this.key = key;
        return this;
    }

    public Card setDashboardRef(final String dashboardRef) {
        this.dashboardRef = dashboardRef;
        return this;
    }

    public Card setText(final String text) {
        this.text = text;
        return this;
    }

    @Override
    public String toString() {
        return "Card{" +
                "key='" + key + '\'' +
                ", dashboardRef='" + dashboardRef + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
