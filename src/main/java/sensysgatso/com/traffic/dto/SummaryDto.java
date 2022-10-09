package sensysgatso.com.traffic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SummaryDto {
    private BigDecimal paid;
    private BigDecimal notPaid;
}
