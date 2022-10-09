package sensysgatso.com.traffic;

import sensysgatso.com.traffic.dto.EventDto;
import sensysgatso.com.traffic.dto.EventType;
import sensysgatso.com.traffic.entity.Event;

import java.time.LocalDate;
import java.util.UUID;

public class EventTestDataFactory {

    public static EventDto createEventDto(EventType type, UUID id) {
        EventDto eventDto = new EventDto();
        eventDto.setId(id);
        eventDto.setEventDate(LocalDate.now());
        eventDto.setEventType(type);
        eventDto.setLicensePlate("007");
        eventDto.setSpeed(60);
        eventDto.setLimit(50);
        eventDto.setUnity("km/h");
        return eventDto;
    }

    public static Event createEvent(EventType type, UUID id) {
        Event event = new Event();
        event.setId(id);
        event.setEventDate(LocalDate.now());
        event.setEventType(type);
        event.setLicensePlate("007");
        event.setSpeed(60);
        event.setSpeedLimit(50);
        event.setUnity("km/h");
        return event;
    }
}
