package sensysgatso.com.traffic.service;

import sensysgatso.com.traffic.dto.EventDto;

import java.util.List;

public interface EventService {
    void processEvent(EventDto eventDto);

    List<EventDto> getEvents();
}
