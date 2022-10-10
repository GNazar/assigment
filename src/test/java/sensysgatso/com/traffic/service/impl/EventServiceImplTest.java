package sensysgatso.com.traffic.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sensysgatso.com.traffic.dto.EventDto;
import sensysgatso.com.traffic.dto.EventType;
import sensysgatso.com.traffic.entity.Event;
import sensysgatso.com.traffic.entity.Violation;
import sensysgatso.com.traffic.repository.EventRepository;
import sensysgatso.com.traffic.service.ViolationService;
import sensysgatso.com.traffic.service.mapper.EventMapper;
import sensysgatso.com.traffic.service.mapper.ViolationMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static sensysgatso.com.traffic.EventTestDataFactory.createEvent;
import static sensysgatso.com.traffic.EventTestDataFactory.createEventDto;
import static sensysgatso.com.traffic.ViolationTestDataFactory.createViolation;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl eventService;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventMapper eventMapper;
    @Mock
    private ViolationMapper violationMapper;
    @Mock
    private ViolationService violationService;

    @Test
    void createEvent_should_createAndProcessViolation() {
        EventType type = EventType.SPEED;
        UUID eventId = UUID.randomUUID();
        EventDto eventDto = createEventDto(type, eventId);
        Event event = createEvent(type, eventId);
        Violation violation = createViolation(eventId);
        when(eventMapper.asEvent(eventDto)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);
        when(violationService.createViolationFromEvent(event)).thenReturn(violation);
        when(violationService.save(violation)).thenReturn(violation);

        eventService.processEvent(eventDto);

        verify(eventMapper).asEvent(eventDto);
        verify(eventRepository, times(2)).save(event);
        verify(violationService).createViolationFromEvent(event);
        verify(violationService).save(violation);
    }

    @Test
    void createEvent_shouldNot_createViolation_when_noViolation() {
        EventType type = EventType.UNKNOWN;
        UUID eventId = UUID.randomUUID();
        EventDto eventDto = createEventDto(type, eventId);
        Event event = createEvent(type, eventId);
        when(eventMapper.asEvent(eventDto)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);

        eventService.processEvent(eventDto);

        verify(eventMapper).asEvent(eventDto);
        verify(eventRepository, times(2)).save(event);
        verify(violationService, never()).createViolationFromEvent(any());
        verify(violationService, never()).save(any());
        verify(violationMapper, never()).asViolationDto(any());
    }
}
