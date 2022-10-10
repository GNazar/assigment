package sensysgatso.com.traffic.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sensysgatso.com.traffic.dto.EventType;
import sensysgatso.com.traffic.dto.SummaryDto;
import sensysgatso.com.traffic.dto.ViolationDto;
import sensysgatso.com.traffic.entity.Event;
import sensysgatso.com.traffic.entity.Violation;
import sensysgatso.com.traffic.repository.ViolationRepository;
import sensysgatso.com.traffic.service.mapper.ViolationMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static sensysgatso.com.traffic.EventTestDataFactory.createEvent;
import static sensysgatso.com.traffic.ViolationTestDataFactory.createViolation;
import static sensysgatso.com.traffic.ViolationTestDataFactory.createViolationDto;

@ExtendWith(MockitoExtension.class)
class ViolationServiceImplTest {

    @InjectMocks
    private ViolationServiceImpl violationService;
    @Mock
    private ViolationRepository violationRepository;
    @Mock
    private ViolationMapper violationMapper;

    @Test
    void getViolations_shouldReturn() {
        UUID eventId = UUID.randomUUID();
        Violation violation1 = createViolation(eventId);
        Pageable pageable = Pageable.ofSize(20);
        PageImpl<Violation> page = new PageImpl<>(List.of(violation1));
        when(violationRepository.findAll(pageable)).thenReturn(page);
        when(violationMapper.asViolationDto(any())).thenReturn(createViolationDto(eventId));

        List<ViolationDto> violations = violationService.getViolations(pageable);

        assertEquals(1, violations.size());
    }

    @Test
    void getSummary_should_countPaidViolations() {
        UUID eventId1 = UUID.randomUUID();
        UUID eventId2 = UUID.randomUUID();
        UUID eventId3 = UUID.randomUUID();
        Violation violation1 = createViolation(eventId1);
        Violation violation2 = createViolation(eventId2);
        violation2.setPaid(true);
        Violation violation3 = createViolation(eventId3);

        when(violationRepository.findAll()).thenReturn(List.of(violation1, violation2, violation3));

        SummaryDto summary = violationService.getSummary();
        assertEquals(BigDecimal.TEN, summary.getPaid());
        assertEquals(BigDecimal.valueOf(20), summary.getNotPaid());
    }

    @Test
    void save_should_saveEntity() {
        UUID eventId = UUID.randomUUID();
        Violation violation = createViolation(eventId);
        when(violationRepository.save(violation)).thenReturn(violation);

        Violation savedViolation = violationService.save(violation);

        assertEquals(violation, savedViolation);
    }

    @Test
    void markAsPaid_shouldSetTrueToPaidProperty() {
        UUID eventId = UUID.randomUUID();
        Violation violation = createViolation(eventId);
        when(violationRepository.findById(eventId)).thenReturn(Optional.of(violation));

        violationService.markAsPaid(eventId);

        ArgumentCaptor<Violation> captor = ArgumentCaptor.forClass(Violation.class);
        verify(violationRepository).save(captor.capture());
        assertTrue(captor.getValue().isPaid());
    }

    @Test
    void createViolationFromEvent_should_createViolation_whenSpeedEvent() {
        UUID id = UUID.randomUUID();
        Event event = createEvent(EventType.SPEED, id);

        Violation violation = violationService.createViolationFromEvent(event);

        assertEquals(id, event.getId());
        assertEquals(BigDecimal.valueOf(50), violation.getFine());
        assertFalse(violation.isPaid());
    }

    @Test
    void createViolationFromEvent_should_throwException_whenUnknownEvent() {
        UUID id = UUID.randomUUID();
        Event event = createEvent(EventType.UNKNOWN, id);

        assertThrows(IllegalArgumentException.class, () -> violationService.createViolationFromEvent(event));
    }
}
