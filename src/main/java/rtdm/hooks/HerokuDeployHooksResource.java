package rtdm.hooks;

import restx.annotations.POST;
import rtdm.hooks.domain.HerokuHookPayload;

public class HerokuDeployHooksResource {

    @POST("/hooks/heroku/:dashboardRef/onDeploy")
    public void onDeployHook(String dashboardRef, HerokuHookPayload payload) {
        // TODO
    }
}
