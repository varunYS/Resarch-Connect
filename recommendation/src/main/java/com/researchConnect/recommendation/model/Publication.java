package com.researchConnect.recommendation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@Table(name = "publications")
public class Publication {
    @Id
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    private String title;

    private String s3Key;

    @Column(name = "context_vector")
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 384)
    private float[] contextVector;
}
