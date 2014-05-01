package rtdm.domain;

/**
 * A link is an URL with meta info.
 */
public class Link {
    /**
     * Link category. Eg BUILD, GITHUB, ...
     */
    private String category;
    /**
     * Link name, eg "build #12345" or "commit 127878df87"
     */
    private String name;
    /**
     * The link URL
     */
    private String url;

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Link setCategory(final String category) {
        this.category = category;
        return this;
    }

    public Link setName(final String name) {
        this.name = name;
        return this;
    }

    public Link setUrl(final String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return "Link{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
