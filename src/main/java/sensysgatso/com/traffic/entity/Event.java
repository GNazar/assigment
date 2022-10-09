package sensysgatso.com.traffic.entity;

import lombok.Data;
import sensysgatso.com.traffic.dto.EventType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;
    private LocalDate eventDate;
    private EventType eventType;
    private String licensePlate;
    private double speed;
    private double speedLimit;
    private String unity;
    private boolean processed;
}
