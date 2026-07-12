package com.researchConnect.recommendation.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    private Long id;

    private String name;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_domains", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "domain")
    private List<String> domains;

    @Column(name = "domain_vector", columnDefinition = "vector")
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 384)
    private float[] domainVector;

}
