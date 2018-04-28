package se.karingotrafiken.timemanager.rest.dto.nonstored.request;

public class CommentRequestDTO {

    private long scheduledDayId;
    private String comment;

    public long getScheduledDayId() {
        return scheduledDayId;
    }

    public void setScheduledDayId(long scheduledDayId) {
        this.scheduledDayId = scheduledDayId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
