package rtdm.hooks;

import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
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

    private static Pattern CARD_REF_PATTERN = Pattern.compile(".*#(\\d+).*");

    private final MongoPersistor persistor;
    private final CardsResource cardsResource;

    public GitHubPushHooksResource(final MongoPersistor persistor,
                                   final CardsResource cardsResource) {
        this.persistor = persistor;
        this.cardsResource = cardsResource;
    }

    @POST("/hooks/github/:dashboardRef/onPush")
    public void onPushHook(String dashboardRef, GitHubHookPayload payload) {

        Optional<Dashboard> dbDashBoard = persistor.getDashboard(dashboardRef);
        if (!dbDashBoard.isPresent()) {
            return;
        }

        for (GitHubCommit commit : payload.getCommits()) {
            Matcher matcher = CARD_REF_PATTERN.matcher(commit.getMessage());
            if (!matcher.matches() || matcher.groupCount() == 0) {
                continue;
            }
            String cardRef = matcher.group(1);
            cardsResource.updateCardStatus(dbDashBoard.get().getKey(), cardRef, Card.Status.PUSHED);
        }
    }
}
