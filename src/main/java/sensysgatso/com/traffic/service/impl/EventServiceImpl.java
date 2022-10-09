package sensysgatso.com.traffic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sensysgatso.com.traffic.dto.EventDto;
import sensysgatso.com.traffic.dto.EventType;
import sensysgatso.com.traffic.entity.Event;
import sensysgatso.com.traffic.entity.Violation;
import sensysgatso.com.traffic.repository.EventRepository;
import sensysgatso.com.traffic.service.EventService;
import sensysgatso.com.traffic.service.ViolationService;
import sensysgatso.com.traffic.service.exception.EventAlreadyProcessed;
import sensysgatso.com.traffic.service.mapper.EventMapper;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ViolationService violationService;

    @Override
    @Async
    public void processEvent(EventDto eventDto) {
        if (eventRepository.findById(eventDto.getId()).isPresent()) {
            throw new EventAlreadyProcessed("Event id: " + "is already processed");
        }
        Event event = eventMapper.asEvent(eventDto);
        Event createdEvent = eventRepository.save(event);
        process(createdEvent);
    }

    @Override
    public List<EventDto> getEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::asEventDto)
                .collect(Collectors.toList());
    }

    private void process(Event event) {
        if (isViolation(event)) {
            Violation violation = violationService.createViolationFromEvent(event);
            violationService.save(violation);
        }
        event.setProcessed(true);
        eventRepository.save(event);
    }


    private boolean isViolation(Event event) {
        return (event.getEventType() == EventType.SPEED)
                || (event.getEventType() == EventType.RED_LIGHT);
    }
}
