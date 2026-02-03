package co.com.bancolombia.transfer.model.gateways;

import co.com.bancolombia.transfer.model.Transfer;
import reactor.core.publisher.Mono;

public interface TransferRepository {
    Mono<Transfer> save(Transfer transfer);
}