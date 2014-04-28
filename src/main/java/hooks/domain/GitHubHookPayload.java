package hooks.domain;

import java.util.List;

public class GitHubHookPayload {

    private List<GitHubCommit> commits;

    public List<GitHubCommit> getCommits() {
        return commits;
    }

    public GitHubHookPayload setCommits(final List<GitHubCommit> commits) {
        this.commits = commits;
        return this;
    }

    @Override
    public String toString() {
        return "GitHubHookPayload{" +
                "commits=" + commits +
                '}';
    }
}
