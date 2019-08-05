package com.mum.edu.blogview.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Data
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

    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Collection<Comment> comments;

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

}
