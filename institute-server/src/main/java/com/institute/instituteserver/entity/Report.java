package com.institute.instituteserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String reportName;
    @NotBlank
    private String reportText;
    @OneToOne
    private User reportOwner;
    @OneToOne
    private Report report;
    @Enumerated(EnumType.STRING)
    private Status status;

    enum Status {
        inWORK,
        APPROVED,
        DECLINED
    }
}
