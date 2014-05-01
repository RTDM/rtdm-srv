package rtdm.domain;

public class GitCommit {

    private String id;
    private String message;

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public GitCommit setId(final String id) {
        this.id = id;
        return this;
    }

    public GitCommit setMessage(final String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "GitCommit{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
