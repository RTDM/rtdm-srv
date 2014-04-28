package rtdm.domain;

public class Dashboard extends Entity {

    private String name;

    public String getName() {
        return name;
    }

    public Dashboard setName(final String name) {
        this.name = name;
        return this;
    }
}
