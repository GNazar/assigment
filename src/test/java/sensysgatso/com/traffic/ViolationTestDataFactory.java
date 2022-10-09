package sensysgatso.com.traffic;

import sensysgatso.com.traffic.dto.ViolationDto;
import sensysgatso.com.traffic.entity.Violation;

import java.math.BigDecimal;
import java.util.UUID;

public class ViolationTestDataFactory {

    public static ViolationDto createViolationDto(UUID eventId) {
        ViolationDto violation = new ViolationDto();
        violation.setFine(BigDecimal.TEN);
        violation.setEventId(eventId);
        return violation;
    }

    public static Violation createViolation(UUID eventId) {
        Violation violation = new Violation();
        violation.setFine(BigDecimal.TEN);
        violation.setEventId(eventId);
        return violation;
    }

}
