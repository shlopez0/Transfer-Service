package co.com.bancolombia.transfer.model;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final TransferError error;

    public BusinessException(TransferError error) {
        super(error.toString());
        this.error = error;
    }
}