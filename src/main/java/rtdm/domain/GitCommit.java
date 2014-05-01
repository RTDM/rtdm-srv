package rtdm.domain;

public class GitCommit {

    private String sha;
    private String message;

    public String getSha() {
        return sha;
    }

    public String getMessage() {
        return message;
    }

    public GitCommit setSha(final String sha) {
        this.sha = sha;
        return this;
    }

    public GitCommit setMessage(final String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "GitCommit{" +
                "sha='" + sha + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
