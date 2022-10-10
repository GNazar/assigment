package sensysgatso.com.traffic.service;

import org.springframework.data.domain.Pageable;
import sensysgatso.com.traffic.dto.SummaryDto;
import sensysgatso.com.traffic.dto.ViolationDto;
import sensysgatso.com.traffic.entity.Event;
import sensysgatso.com.traffic.entity.Violation;

import java.util.List;
import java.util.UUID;

public interface ViolationService {

    List<ViolationDto> getViolations(Pageable pageable);

    Violation createViolationFromEvent(Event event);

    Violation save(Violation violation);

    void markAsPaid(UUID id);

    SummaryDto getSummary();
}
