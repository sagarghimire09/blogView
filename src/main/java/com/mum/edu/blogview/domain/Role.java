package com.mum.edu.blogview.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private int id;
    @Column(name = "role")
    private String role;
}