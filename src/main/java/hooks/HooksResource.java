package hooks;

import hooks.domain.DroneHookPayload;
import hooks.domain.GitHubCommit;
import hooks.domain.GitHubHookPayload;
import hooks.domain.HerokuHookPayload;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import rtdm.domain.Card;
import rtdm.domain.Dashboard;
import rtdm.persistence.MongoPersistor;
import rtdm.rest.CardsResource;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RestxResource
public class HooksResource {

    private static Pattern CARD_REF_PATTERN = Pattern.compile(".*#(\\d+).*");

    private final MongoPersistor persistor;
    private final CardsResource cardsResource;

    public HooksResource(final MongoPersistor persistor,
                         final CardsResource cardsResource) {
        this.persistor = persistor;
        this.cardsResource = cardsResource;
    }

    @POST("/hooks/:dashboardRef/onPush")
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

    @POST("/hooks/:dashboardRef/onBuild")
    public void onBuildHook(String dashboardRef, DroneHookPayload payload) {
        // TODO
    }

    @POST("/hooks/:dashboardRef/onDeploy")
    public void onDeployHook(String dashboardRef, HerokuHookPayload payload) {
        // TODO
    }
}
