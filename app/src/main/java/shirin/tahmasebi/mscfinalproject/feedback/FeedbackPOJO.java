package shirin.tahmasebi.mscfinalproject.feedback;

public class FeedbackPOJO {
    private String message;
    private String subject;

    public FeedbackPOJO() {
    }

    public FeedbackPOJO(String message, String subject) {
        this.message = message;
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
