package com.edu.mum.domain;

import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    @ToString.Exclude
    private Category category;

    @Column(name = "title", nullable = false)
    @NotEmpty(message = "*Please provide title")
    private String title;

    @Lob
    @Column(name = "body", columnDefinition = "TEXT")
    @NotNull(message = "*Please provide the content")
    private String body;
    private double earning;
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

    @OneToMany(mappedBy = "post")
    @ToString.Exclude
    private Collection<Review> reviews;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Payment payment;

    private boolean claimedStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }

    public boolean isClaimedStatus() {
        return claimedStatus;
    }

    public void setClaimedStatus(boolean claimedStatus) {
        this.claimedStatus = claimedStatus;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Post(){

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

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }
}
