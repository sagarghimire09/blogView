package com.edu.mum.domain;

import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private  Long accountId;

    @NotNull(message = "* Contact Number cannot be null")

    private Long contactNumber;


    @NotBlank(message = "Bank name is required")
    private String bank;



    @NotNull(message = "* Account Number cannot be null")

    private Long accountNumber;

    @NotBlank(message = "Swift Code is required")
    private String swiftCode;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<Payment> payments;

    public Account(){

    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }
    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", contactNumber=" + contactNumber +
                ", bank='" + bank + '\'' +
                ", accountNumber=" + accountNumber +
                ", swiftCode='" + swiftCode + '\'' +
                ", user=" + user +
                '}';
    }
}
