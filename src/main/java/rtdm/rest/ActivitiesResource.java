package rtdm.rest;

import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import rtdm.domain.Activity;
import rtdm.persistence.MongoPersistor;

@Component
@RestxResource
public class ActivitiesResource {

    private final MongoPersistor persistor;

    public ActivitiesResource(final MongoPersistor persistor) {
        this.persistor = persistor;
    }

    @GET("/activities")
    public Iterable<Activity> getActivities() {
        return persistor.getActivities();
    }

}
