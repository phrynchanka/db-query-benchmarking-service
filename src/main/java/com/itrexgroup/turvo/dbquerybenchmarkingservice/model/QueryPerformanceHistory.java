package com.itrexgroup.turvo.dbquerybenchmarkingservice.model;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsExecutionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "query_performance_history")
@Entity
public class QueryPerformanceHistory {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @JoinColumn(name = "query_performance_id")
    @ManyToOne(fetch = EAGER)
    private QueryPerformance queryPerformance;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    private MetricsExecutionStatus status;

    @Column
    private String result;

    @NotNull
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
