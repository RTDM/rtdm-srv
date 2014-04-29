package rtdm.hooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import rtdm.domain.Card;
import rtdm.domain.Dashboard;
import rtdm.hooks.domain.GitHubCommit;
import rtdm.hooks.domain.GitHubHookPayload;
import rtdm.persistence.MongoPersistor;
import rtdm.rest.CardsResource;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RestxResource
public class GitHubPushHooksResource {
    private static final Logger logger = LoggerFactory.getLogger(GitHubPushHooksResource.class);

    private static Pattern CARD_REF_PATTERN = Pattern.compile(".*#(\\d+).*");

    private final MongoPersistor persistor;
    private final CardsResource cardsResource;

    public GitHubPushHooksResource(final MongoPersistor persistor,
                                   final CardsResource cardsResource) {
        this.persistor = persistor;
        this.cardsResource = cardsResource;
    }

    @PermitAll
    @POST("/hooks/github/:dashboardRef/onPush")
    public void onPushHook(String dashboardRef, GitHubHookPayload payload) {
        logger.info("received GitHub push hook request for {}: {}", dashboardRef, payload);

        Optional<Dashboard> dbDashBoard = persistor.getDashboard(dashboardRef);
        if (!dbDashBoard.isPresent()) {
            logger.warn("dashboard not found for GitHub push hook request on {}", dashboardRef);
            return;
        }

        for (GitHubCommit commit : payload.getCommits()) {
            Matcher matcher = CARD_REF_PATTERN.matcher(commit.getMessage());
            if (!matcher.matches() || matcher.groupCount() == 0) {
                continue;
            }
            String cardRef = matcher.group(1);
            logger.info("updating card {} / {} status triggered by github hook", dashboardRef, cardRef);
            cardsResource.updateCardStatus(dbDashBoard.get().getKey(), cardRef, Card.Status.PUSHED);
        }
    }
}
