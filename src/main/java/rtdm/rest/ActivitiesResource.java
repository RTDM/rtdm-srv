package rtdm.rest;

import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import rtdm.domain.Activity;
import rtdm.persistence.MongoPersistor;

@Component
@RestxResource
public class ActivitiesResource {

    private final MongoPersistor persistor;

    public ActivitiesResource(final MongoPersistor persistor) {
        this.persistor = persistor;
    }

    @PermitAll
    @GET("/activities")
    public Iterable<Activity> getActivities() {
        return persistor.getActivities();
    }

}
