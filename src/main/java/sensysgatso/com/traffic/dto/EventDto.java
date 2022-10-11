package sensysgatso.com.traffic.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;
import javax.validation.constraints.*;

@Data
public class EventDto {
    @NotNull
    private UUID id;
    @NotNull
    private LocalDate eventDate;
    @NotNull
    private EventType eventType;
    @NotBlank
    private String licensePlate;
    @PositiveOrZero
    private double speed;
    @Positive
    private double limit;
    @NotBlank
    private String unity;
    private boolean processed;
}
