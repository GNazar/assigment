package sensysgatso.com.traffic.service.mapper;

import org.springframework.stereotype.Component;
import sensysgatso.com.traffic.dto.EventDto;
import sensysgatso.com.traffic.entity.Event;
import org.mapstruct.*;

@Mapper
@Component
public interface EventMapper {
    @Mapping(source = "speedLimit", target = "limit")
    EventDto asEventDto(Event event);

    @Mapping(source = "limit", target = "speedLimit")
    Event asEvent(EventDto event);
}
