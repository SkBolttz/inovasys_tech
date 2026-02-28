package br.com.Inovasys.infra.exceptions.ClienteException;

public class DuplicidadeEmailClienteException extends RuntimeException {
    public DuplicidadeEmailClienteException(String message) {
        super(message);
    }
}
