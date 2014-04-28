package rtdm.hooks;

import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import rtdm.hooks.domain.HerokuHookPayload;

@Component
@RestxResource
public class HerokuDeployHooksResource {

    @POST("/hooks/heroku/:dashboardRef/onDeploy")
    public void onDeployHook(String dashboardRef, HerokuHookPayload payload) {
        // TODO
    }
}
