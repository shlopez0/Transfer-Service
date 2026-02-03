package co.com.bancolombia.transfer.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransferError {
    SAME_ACCOUNT("La cuenta origen y destino no pueden ser iguales"),
    INSUFFICIENT_FUNDS("Fondos insuficientes para la operación"),
    ACCOUNT_INACTIVE("La cuenta destino no se encuentra activa"),
    DAILY_LIMIT_EXCEEDED("Supera el tope diario permitido"),
    OUTSIDE_HOURS("Operación no permitida en este horario"),
    TECHNICAL_ERROR("Error técnico en el sistema core");

    private final String message;
}