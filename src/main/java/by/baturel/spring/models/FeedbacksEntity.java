package by.baturel.spring.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Feedbacks", schema = "dbo", catalog = "Hotels")
public class FeedbacksEntity {
    private Long id;
    private int userId;
    private int hotelId;
    private Date feedbackDate;
    private String comment;
    private UsersEntity usersByUserId;
    private HotelsEntity hotelsByHotelId;

    public FeedbacksEntity(int userId, int hotelId, Date feedbackDate, String comment) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.feedbackDate = feedbackDate;
        this.comment = comment;
    }

    public FeedbacksEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "UserId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "HotelId")
    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    @Basic
    @Column(name = "FeedbackDate")
    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Basic
    @Column(name = "Comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbacksEntity that = (FeedbacksEntity) o;

        if (userId != that.userId) return false;
        if (hotelId != that.hotelId) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (feedbackDate != null ? !feedbackDate.equals(that.feedbackDate) : that.feedbackDate != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + userId;
        result = 31 * result + hotelId;
        result = 31 * result + (feedbackDate != null ? feedbackDate.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "HotelId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public HotelsEntity getHotelsByHotelId() {
        return hotelsByHotelId;
    }

    public void setHotelsByHotelId(HotelsEntity hotelsByHotelId) {
        this.hotelsByHotelId = hotelsByHotelId;
    }
}
