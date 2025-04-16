package ru.sterkhov_kirill.NauJava.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sterkhov_kirill.NauJava.enums.ReportStatus;

@Entity
@Data
@NoArgsConstructor
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.CREATED;

    @Lob
    private String content = "";
}
