package by.baturel.spring.DTO;

import by.baturel.spring.models.HotelsEntity;
import by.baturel.spring.models.UsersEntity;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import java.sql.Date;


public class FeedBackForm {
    private String username;
    private Date feedbackDate;
    private String comment;

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public String getUsername() {
        return username;
    }


    public FeedBackForm(Long id, String username, Date feedbackDate, String comment) {
        this.username = username;
        this.feedbackDate = feedbackDate;
        this.comment = comment;
    }

    public FeedBackForm() {
    }
}
