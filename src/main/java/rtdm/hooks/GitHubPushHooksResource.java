package rtdm.hooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import rtdm.domain.Card;
import rtdm.domain.Card.Status;
import rtdm.domain.Dashboard;
import rtdm.domain.GitCommit;
import rtdm.domain.Link;
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
    @POST("/hooks/github/:org/:repo/:dashboardKey/onPush")
    public void onPushHook(String org, String repo, String dashboardKey, GitHubHookPayload payload) {
        logger.info("received GitHub push hook request for {}: {}", dashboardKey, payload);

        Optional<Dashboard> dbDashBoard = persistor.getDashboardByKey(dashboardKey);
        if (!dbDashBoard.isPresent()) {
            logger.warn("dashboard not found for GitHub push hook request on {}", dashboardKey);
            return;
        }

        for (GitCommit commit : payload.getCommits()) {
            Matcher matcher = CARD_REF_PATTERN.matcher(commit.getMessage());
            if (!matcher.matches() || matcher.groupCount() == 0) {
                continue;
            }
            String cardRef = matcher.group(1);
            Optional<Card> card = cardsResource.findCardByRef(dbDashBoard.get().getKey(), cardRef);
            if (card.isPresent()) {
                logger.info("updating card {} / {} status triggered by github hook", dbDashBoard.get().getName(), cardRef);
                card.get().setStatus(Status.PUSHED);
                card.get().getCommits().add(commit);
                card.get().getLinks().add(new Link()
                        .setCategory("GitHub")
                        .setName("commit " + commit.getId())
                        .setUrl("https://github.com/" + org + "/" + repo + "/commit/" + commit.getId())
                );
                cardsResource.updateCard(dbDashBoard.get().getKey(), card.get().getKey(), card.get());
            }
        }
    }
}
