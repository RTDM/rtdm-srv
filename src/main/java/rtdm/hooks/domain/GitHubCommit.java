package rtdm.hooks.domain;

public class GitHubCommit {

    private String message;

    public String getMessage() {
        return message;
    }

    public GitHubCommit setMessage(final String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "GitHubCommit{" +
                "message='" + message + '\'' +
                '}';
    }
}
