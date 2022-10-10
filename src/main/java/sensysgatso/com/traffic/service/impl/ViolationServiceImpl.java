package sensysgatso.com.traffic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sensysgatso.com.traffic.dto.SummaryDto;
import sensysgatso.com.traffic.dto.ViolationDto;
import sensysgatso.com.traffic.entity.Event;
import sensysgatso.com.traffic.entity.Violation;
import sensysgatso.com.traffic.repository.ViolationRepository;
import sensysgatso.com.traffic.service.ViolationService;
import sensysgatso.com.traffic.service.exception.ViolationNotFound;
import sensysgatso.com.traffic.service.mapper.ViolationMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ViolationServiceImpl implements ViolationService {

    public static final int SPEED_VIOLATION_FINE = 50;
    public static final int RED_LIGHT_VIOLATION_FINE = 100;
    private final ViolationRepository violationRepository;
    private final ViolationMapper violationMapper;

    @Override
    public List<ViolationDto> getViolations() {
        return violationRepository.findAll()
                .stream()
                .map(violationMapper::asViolationDto)
                .collect(Collectors.toList());
    }

    @Override
    public SummaryDto getSummary() {
        BigDecimal paid = BigDecimal.ZERO;
        BigDecimal notPaid = BigDecimal.ZERO;

        for (Violation violation : violationRepository.findAll()) {
            if (violation.isPaid()) {
                paid = paid.add(violation.getFine());
            } else {
                notPaid = notPaid.add(violation.getFine());
            }
        }
        return new SummaryDto(paid, notPaid);
    }

    @Override
    public Violation save(Violation violation) {
        return violationRepository.save(violation);
    }

    @Override
    public void markAsPaid(UUID id) {
        violationRepository.findById(id)
                .ifPresentOrElse(this::payViolation,
                        () -> {
                            throw new ViolationNotFound("Violation " + id + "is not found.");
                        });
    }

    @Override
    public Violation createViolationFromEvent(Event event) {
        Violation violation = new Violation();
        violation.setEvent(event);
        violation.setFine(getFine(event));
        return violation;
    }

    private Violation payViolation(Violation v) {
        v.setPaid(true);
        return violationRepository.save(v);
    }

    private BigDecimal getFine(Event event) {
        switch (event.getEventType()) {
            case SPEED:
                return BigDecimal.valueOf(SPEED_VIOLATION_FINE);
            case RED_LIGHT:
                return BigDecimal.valueOf(RED_LIGHT_VIOLATION_FINE);
            default:
                throw new IllegalArgumentException("No fine defined for event: " + event.getEventType());
        }
    }


}
