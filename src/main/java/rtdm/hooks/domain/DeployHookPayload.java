package rtdm.hooks.domain;

/**
 * A generic payload for build hook.
 *
 * This is the kind of information you can get from jenkins or drone for instance.
 */
public class DeployHookPayload {
    private String deployURL;
    private String commitHash;

    public String getDeployURL() {
        return deployURL;
    }

    public String getCommitHash() {
        return commitHash;
    }

    public DeployHookPayload setDeployURL(final String deployURL) {
        this.deployURL = deployURL;
        return this;
    }

    public DeployHookPayload setCommitHash(final String commitHash) {
        this.commitHash = commitHash;
        return this;
    }

    @Override
    public String toString() {
        return "BuildHookPayload{" +
                "deployURL='" + deployURL + '\'' +
                ", commitHash='" + commitHash + '\'' +
                '}';
    }
}
