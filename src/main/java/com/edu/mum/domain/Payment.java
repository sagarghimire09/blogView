package com.edu.mum.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private long paymentId;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date payDate;

    private double payAmount;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    private Boolean status;

    public Payment(){

    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", payDate=" + payDate +
                ", payAmount=" + payAmount +
                ", account=" + account +
                ", user=" + user +
                ", post=" + post +
                ", status=" + status +
                '}';
    }
}
