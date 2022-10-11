package sensysgatso.com.traffic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sensysgatso.com.traffic.dto.EventDto;
import sensysgatso.com.traffic.service.EventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createEvent(@Valid @RequestBody EventDto eventDto) {
        eventService.processEvent(eventDto);
    }

    //todo: for test, delete
    @GetMapping
    public List<EventDto> getEvents() {
        return eventService.getEvents();
    }

}
