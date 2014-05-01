package rtdm.persistence;

import rtdm.domain.Card;
import events.EventBrokerEvent;

public final class CardEvent extends EventBrokerEvent {
    private final Card card;

    public CardEvent(String eventType, Card card) {
        super(eventType);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() {
        return "CardEvent{" +
                "card=" + card +
                '}';
    }
}
