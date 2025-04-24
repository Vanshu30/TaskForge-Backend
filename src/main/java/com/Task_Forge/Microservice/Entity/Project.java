package com.Task_Forge.Microservice.Entity;


import com.Task_Forge.Microservice.ENUM.ProjectPhase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Enumerated(EnumType.STRING)
    private ProjectPhase phase;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
