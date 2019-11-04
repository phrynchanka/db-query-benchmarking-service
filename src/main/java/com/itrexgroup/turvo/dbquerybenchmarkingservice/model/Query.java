package com.itrexgroup.turvo.dbquerybenchmarkingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "query", uniqueConstraints = @UniqueConstraint(columnNames = {"queryName", "version"}))
@Entity
public class Query {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String queryName;

    @NotNull
    @Column
    private String sql;

    @NotNull
    @Column
    private String version;
}
