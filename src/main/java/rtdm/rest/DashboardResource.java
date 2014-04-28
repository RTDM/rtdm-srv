package rtdm.rest;

import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import rtdm.domain.Dashboard;
import rtdm.persistence.MongoPersistor;

@Component
@RestxResource
public class DashboardResource {

    private final MongoPersistor persistor;

    public DashboardResource(final MongoPersistor persistor) {
        this.persistor = persistor;
    }

    @GET("/dashboard")
    public Iterable<Dashboard> getDashboards() {
        return persistor.getDashboards();
    }

    @POST("/dashboard")
    public Iterable<Dashboard> addDashboard(Dashboard dashboard) {
        persistor.createOrUpdateDashboard(dashboard);
        return persistor.getDashboards();
    }

}
