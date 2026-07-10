package com.researchConnect.publication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    private Long id;

    private String name;

    private String email;

    private String institution;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_domains", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "domain")
    private List<String> domains;

}
