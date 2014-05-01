package rtdm.hooks.domain;

/**
 * A generic payload for build hook.
 *
 * This is the kind of information you can get from jenkins or drone for instance.
 */
public class BuildHookPayload {
    private String buildURL;
    private String buildNumber;
    private String commitHash;

    public String getBuildURL() {
        return buildURL;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public String getCommitHash() {
        return commitHash;
    }

    public BuildHookPayload setBuildURL(final String buildURL) {
        this.buildURL = buildURL;
        return this;
    }

    public BuildHookPayload setBuildNumber(final String buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public BuildHookPayload setCommitHash(final String commitHash) {
        this.commitHash = commitHash;
        return this;
    }

    @Override
    public String toString() {
        return "BuildHookPayload{" +
                "buildURL='" + buildURL + '\'' +
                ", buildNumber='" + buildNumber + '\'' +
                ", commitHash='" + commitHash + '\'' +
                '}';
    }
}
