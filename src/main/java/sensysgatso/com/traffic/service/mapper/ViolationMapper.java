package sensysgatso.com.traffic.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import sensysgatso.com.traffic.dto.ViolationDto;
import sensysgatso.com.traffic.entity.Violation;

@Mapper
@Component
public interface ViolationMapper {
    @Mapping(source = "event.id", target = "eventId")
    ViolationDto asViolationDto(Violation violation);

    Violation asViolation(ViolationDto violationDto);
}
