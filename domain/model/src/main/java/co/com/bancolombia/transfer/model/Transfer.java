package co.com.bancolombia.transfer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    private String id;
    private String sourceAccount;
    private String targetAccount;
    private BigDecimal amount;
    private String description;
}