package sensysgatso.com.traffic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sensysgatso.com.traffic.dto.SummaryDto;
import sensysgatso.com.traffic.dto.ViolationDto;
import sensysgatso.com.traffic.service.ViolationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("violation")
@RequiredArgsConstructor
public class ViolationController {

    private final ViolationService violationService;

    @GetMapping
    public List<ViolationDto> getViolations() {
        return violationService.getViolations();
    }

    @PutMapping("{id}/paid")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markAsPaid(@PathVariable UUID id) {
        violationService.markAsPaid(id);
    }

    @GetMapping("summary")
    public SummaryDto getSummary() {
        return violationService.getSummary();
    }

}
