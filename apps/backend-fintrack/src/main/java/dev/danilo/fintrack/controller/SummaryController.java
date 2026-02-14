package dev.danilo.fintrack.controller;

import dev.danilo.fintrack.dto.response.SummaryResponse;
import dev.danilo.fintrack.service.SummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/summary")
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping
    public ResponseEntity<SummaryResponse> getSummary(
            @RequestParam(required = false) String month
    ) {
        return ResponseEntity.ok(
                summaryService.getSummary(month)
        );
    }
}
