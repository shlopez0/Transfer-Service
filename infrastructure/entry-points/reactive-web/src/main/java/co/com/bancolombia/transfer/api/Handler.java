package co.com.bancolombia.transfer.api;

import co.com.bancolombia.transfer.model.BusinessException;
import co.com.bancolombia.transfer.model.Transfer;
import co.com.bancolombia.transfer.model.TransferError;
import co.com.bancolombia.transfer.usecase.TransferUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final TransferUseCase transferUseCase;

    public Mono<ServerResponse> createTransfer(ServerRequest request) {
        return request.bodyToMono(Transfer.class)
                .flatMap(transfer -> transferUseCase.createTransfer(transfer)
                        .flatMap(savedTransfer -> ServerResponse.ok().bodyValue(savedTransfer)))
                .onErrorResume(e -> {
                    if (e instanceof BusinessException businessEx) {
                        HttpStatus status = mapErrorToStatus(businessEx.getError());
                        return ServerResponse.status(status).bodyValue(businessEx.getMessage());
                    }
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    private HttpStatus mapErrorToStatus(TransferError error) {
        return switch (error) {
            case SAME_ACCOUNT -> HttpStatus.BAD_REQUEST;
            case INSUFFICIENT_FUNDS -> HttpStatus.UNPROCESSABLE_ENTITY;
            case DAILY_LIMIT_EXCEEDED -> HttpStatus.CONFLICT;
            case ACCOUNT_INACTIVE -> HttpStatus.FORBIDDEN;
            case OUTSIDE_HOURS -> HttpStatus.LOCKED;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}