package rtdm.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 27/4/14
 * Time: 20:37
 */
public class Card extends Entity {

    public enum Status {
        TODO, DOING, PUSHED, BUILT, DEPLOYED, ERROR
    }

    private String ref;

    private String dashboardKey;

    private String text;

    private Status status;

    private List<GitCommit> commits = new ArrayList<>();

    public String getRef() {
        return ref;
    }

    public String getDashboardKey() {
        return dashboardKey;
    }

    public String getText() {
        return text;
    }

    public List<GitCommit> getCommits() {
        return commits;
    }

    public Card setRef(final String ref) {
        this.ref = ref;
        return this;
    }

    public Card setDashboardKey(final String dashboardKey) {
        this.dashboardKey = dashboardKey;
        return this;
    }

    public Card setText(final String text) {
        this.text = text;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Card setStatus(final Status status) {
        this.status = status;
        return this;
    }

    public Card setCommits(final List<GitCommit> commits) {
        this.commits = commits;
        return this;
    }

    @Override
    public String toString() {
        return "Card{" +
                "ref='" + ref + '\'' +
                ", dashboardKey='" + dashboardKey + '\'' +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", commits=" + commits +
                '}';
    }
}
