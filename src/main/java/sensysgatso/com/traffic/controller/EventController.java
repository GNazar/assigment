package sensysgatso.com.traffic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sensysgatso.com.traffic.dto.EventDto;
import sensysgatso.com.traffic.service.EventService;

import java.util.List;

@RestController
@RequestMapping("event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createEvent(@RequestBody EventDto eventDto) {
        eventService.processEvent(eventDto);
    }

    @GetMapping
    public List<EventDto> getEvents() {
        return eventService.getEvents();
    }

}
