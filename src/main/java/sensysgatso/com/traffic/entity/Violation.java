package sensysgatso.com.traffic.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "violations")
public class Violation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;
    @Column(columnDefinition = "uuid")
    private UUID eventId;
    private boolean paid;
    private BigDecimal fine;

}
