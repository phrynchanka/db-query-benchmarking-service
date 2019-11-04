package com.itrexgroup.turvo.dbquerybenchmarkingservice.model;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "query_performance")
@Entity
@EqualsAndHashCode(exclude = {"history"})
@ToString(exclude = {"history"})
public class QueryPerformance {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @JoinColumn(name = "query_id")
    @ManyToOne(fetch = EAGER, cascade = PERSIST)
    private Query query;

    @NotNull
    @Column
    private String database;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    private MetricsType type;

    @OneToMany(mappedBy = "queryPerformance")
    private List<QueryPerformanceHistory> history;
}
