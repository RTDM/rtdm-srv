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
import rtdm.hooks.domain.DeployHookPayload;
import rtdm.rest.CardsResource;

@Component
@RestxResource
public class DeployHooksResource {
    private static final Logger logger = LoggerFactory.getLogger(DeployHooksResource.class);

    private final CardsResource cardsResource;

    public DeployHooksResource(CardsResource cardsResource) {
        this.cardsResource = cardsResource;
    }

    /**
     * A generic deploy hook.
     *
     * Example of call with curl:
     * <code>
     * curl -X POST -H "Content-Type: application/json" -d "{\"commitHash\":\"$GIT_COMMIT\",\"deployURL\":\"$BUILD_URL\"}" http://rtdm.restx.io/api/hooks/deploy/535eb9a5975a9d56a4f5d706/onDeploy
     * </code>
     * @param dashboardKey the key of the dashboard containing the card
     * @param payload the deploy hook payload
     */
    @PermitAll
    @POST("/hooks/deploy/:dashboardKey/onDeploy")
    public void onDeployHook(String dashboardKey, DeployHookPayload payload) {
        Iterable<Card> cards = cardsResource.findCardByCommitHash(payload.getCommitHash());

        if (Iterables.isEmpty(cards)) {
            logger.info("deploy hook didn't match any card: {}", payload);
            return;
        }

        for (Card card : cards) {
            if (!card.getDashboardKey().equals(dashboardKey)) {
                logger.debug(
                        "found a card matching deploy hook commit but not dashboardKey: " +
                                "dashboardKey={}, card={}, payload={}",
                        dashboardKey, card, payload);
                continue;
            }
            logger.info("updating card {} / {} status triggered by deploy hook {}",
                    card.getDashboardKey(), card.getKey(), payload);
            card.setStatus(Status.DEPLOYED);
            card.getLinks().add(new Link()
                    .setCategory("Deploy")
                    .setName("deploy " + payload.getCommitHash())
                    .setUrl(payload.getDeployURL()));
            cardsResource.updateCard(card.getDashboardKey(), card.getKey(),
                    card.setStatus(Status.DEPLOYED));
        }
    }
}
