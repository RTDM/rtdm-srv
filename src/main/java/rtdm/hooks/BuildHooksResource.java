package rtdm.hooks;

import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import rtdm.domain.Card;
import rtdm.domain.Card.Status;
import rtdm.domain.Link;
import rtdm.hooks.domain.BuildHookPayload;
import rtdm.rest.CardsResource;
import rtdm.rest.DashboardResource;

@Component
@RestxResource
public class BuildHooksResource {
    private static final Logger logger = LoggerFactory.getLogger(BuildHooksResource.class);

    private final CardsResource cardsResource;

    public BuildHooksResource(CardsResource cardsResource) {
        this.cardsResource = cardsResource;
    }

    @PermitAll
    @POST("/hooks/build/:dashboardKey/onBuild")
    public void onBuildHook(String dashboardKey, BuildHookPayload payload) {
        Iterable<Card> cards = cardsResource.findCardByCommitHash(payload.getCommitHash());

        if (Iterables.isEmpty(cards)) {
            logger.info("build hook didn't match any card: {}", payload);
            return;
        }

        for (Card card : cards) {
            card.setStatus(Status.BUILT);
            card.getLinks().add(new Link()
                    .setCategory("Build")
                    .setName("build " + payload.getBuildNumber())
                    .setUrl(payload.getBuildURL()));
            cardsResource.updateCard(card.getDashboardKey(), card.getKey(),
                    card.setStatus(Status.BUILT));
        }
    }
}
