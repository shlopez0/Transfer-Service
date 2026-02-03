package co.com.bancolombia.transfer.usecase;

import co.com.bancolombia.transfer.model.BusinessException;
import co.com.bancolombia.transfer.model.Transfer;
import co.com.bancolombia.transfer.model.TransferError;
import co.com.bancolombia.transfer.model.gateways.TransferRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalTime;

@RequiredArgsConstructor
public class TransferUseCase {

    private final TransferRepository repository;

    public Mono<Transfer> createTransfer(Transfer transfer) {
        // 1. Regla: Cuentas Iguales
        if (transfer.getSourceAccount().equals(transfer.getTargetAccount())) {
            return Mono.error(new BusinessException(TransferError.SAME_ACCOUNT));
        }

        // 2. Simulación: Cuenta Inactiva (Empieza con 99)
        if (transfer.getTargetAccount().startsWith("99")) {
            return Mono.error(new BusinessException(TransferError.ACCOUNT_INACTIVE));
        }

        // 3. Regla: Fondos Insuficientes (> 10 Millones)
        if (transfer.getAmount().compareTo(BigDecimal.valueOf(10000000)) > 0) {
            return Mono.error(new BusinessException(TransferError.INSUFFICIENT_FUNDS));
        }

        // 4. Simulación: Tope Diario (Monto exacto de 5 Millones)
        if (transfer.getAmount().compareTo(BigDecimal.valueOf(5000000)) == 0) {
            return Mono.error(new BusinessException(TransferError.DAILY_LIMIT_EXCEEDED));
        }

        // 5. Regla: Horario no hábil (Antes de las 8 AM o después de las 8 PM)
        LocalTime now = LocalTime.now();
        if (now.isBefore(LocalTime.of(8, 0)) || now.isAfter(LocalTime.of(20, 0))) {
            return Mono.error(new BusinessException(TransferError.OUTSIDE_HOURS));
        }

        // 6. Simulación Técnica
        if (transfer.getDescription() != null && transfer.getDescription().contains("ERROR")) {
            return Mono.error(new RuntimeException("Error técnico simulado"));
        }

        return repository.save(transfer);
    }
}