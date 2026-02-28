package br.com.Inovasys.infra.Exceptions.ClienteException;

public class ClienteNaoLocalizadoException extends RuntimeException {
    public ClienteNaoLocalizadoException(String message) {
        super(message);
    }
}

