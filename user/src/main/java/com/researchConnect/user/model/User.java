package com.researchConnect.user.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @ToString.Exclude
    private String password;

    private String institution;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_domains", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "domain")
    private List<String> domains;

}
