package sensysgatso.com.traffic.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class EventDto {
    private UUID id;
    private LocalDate eventDate;
    private EventType eventType;
    private String licensePlate;
    private double speed;
    private double limit;
    private String unity;
    private boolean processed;
}
