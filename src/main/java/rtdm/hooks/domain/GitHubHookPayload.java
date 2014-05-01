package rtdm.hooks.domain;

import rtdm.domain.GitCommit;

import java.util.List;

public class GitHubHookPayload {

    private List<GitCommit> commits;

    public List<GitCommit> getCommits() {
        return commits;
    }

    public GitHubHookPayload setCommits(final List<GitCommit> commits) {
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
