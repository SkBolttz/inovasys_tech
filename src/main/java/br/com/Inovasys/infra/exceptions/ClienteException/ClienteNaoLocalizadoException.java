package br.com.Inovasys.infra.exceptions.ClienteException;

public class ClienteNaoLocalizadoException extends RuntimeException {
    public ClienteNaoLocalizadoException(String message) {
        super(message);
    }
}

