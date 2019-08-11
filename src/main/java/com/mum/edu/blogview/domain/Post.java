package com.edu.mum.domain;

import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String category;
    @Column(name = "title", nullable = false)
    @NotEmpty(message = "*Please provide title")
    private String title;

    @Lob
    @Column(name = "body", columnDefinition = "TEXT")
    @NotNull(message = "*Please provide the content")
    private String body;
    private double avgRating;
    private int ratedCount;
//    @Lob
//    private byte[] coverImage;
    private String coverImage;
    private boolean status;
    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;


    @ManyToOne(optional = false)
    @JoinColumn( name = "user_id", referencedColumnName = "user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "post")
    @ToString.Exclude
    private Collection<Comment> comments;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public int getRatedCount() {
        return ratedCount;
    }

    public void setRatedCount(int ratedCount) {
        this.ratedCount = ratedCount;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }



    public Post(){

    }

    public void updateRatedCount() {
        this.ratedCount++;
    }

    public void updateAvgRating(double userRating) {
        System.out.println("user rating= "+ userRating);
        int ratedCont = getRatedCount()+1;
        double prevRating = getAvgRating();
        System.out.println("previous rating  = "+ prevRating);
        double totalRating = prevRating+userRating;
        System.out.println("total rating = "+totalRating);
        this.avgRating = totalRating/ratedCont;
        System.out.println("final user ratin = "+ getAvgRating());
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", avgRating=" + avgRating +
                ", ratedCount=" + ratedCount +
                ", coverImage='" + coverImage + '\'' +
                ", createDate=" + createDate +
                ", status=" + status +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
