package co.com.bancolombia.transfer.adapter;

import co.com.bancolombia.transfer.model.Transfer;
import co.com.bancolombia.transfer.model.gateways.TransferRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID; // 1. Importamos la librería de UUID

@Repository
public class InMemoryRepository implements TransferRepository {

    private final List<Transfer> transfers = new ArrayList<>();

    @Override
    public Mono<Transfer> save(Transfer transfer) {
        transfer.setId(UUID.randomUUID().toString());

        transfers.add(transfer);
        return Mono.just(transfer);
    }
}