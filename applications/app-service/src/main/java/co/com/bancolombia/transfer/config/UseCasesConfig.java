package co.com.bancolombia.transfer.config;

import co.com.bancolombia.transfer.model.gateways.TransferRepository;
import co.com.bancolombia.transfer.usecase.TransferUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public TransferUseCase transferUseCase(TransferRepository repository) {
        return new TransferUseCase(repository);
    }
}