package ru.sterkhov_kirill.NauJava.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sterkhov_kirill.NauJava.service.ReportService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<String> getReport(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getContent(id));
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<Long> createReport() {
        Long id = reportService.createReport();
        reportService.generateReport(id);

        return ResponseEntity.ok(id);
    }
}
