package rtdm.hooks;

import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import rtdm.hooks.domain.DroneHookPayload;

@Component
@RestxResource
public class DroneBuildHooksResource {

    @POST("/hooks/drone/:dashboardRef/onBuild")
    public void onBuildHook(String dashboardRef, DroneHookPayload payload) {
        // TODO
    }
}
