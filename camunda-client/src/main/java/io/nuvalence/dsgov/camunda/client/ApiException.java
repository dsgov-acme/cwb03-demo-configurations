package io.nuvalence.dsgov.camunda.client;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
