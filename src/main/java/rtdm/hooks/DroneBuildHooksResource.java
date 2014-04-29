package rtdm.hooks;

import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import rtdm.hooks.domain.DroneHookPayload;

@Component
@RestxResource
public class DroneBuildHooksResource {

    @PermitAll
    @POST("/hooks/drone/:dashboardRef/onBuild")
    public void onBuildHook(String dashboardRef, DroneHookPayload payload) {
        // TODO
    }
}
