package br.com.Inovasys.infra.Exceptions.ClienteException;

public class DuplicidadeEmailClienteException extends RuntimeException {
    public DuplicidadeEmailClienteException(String message) {
        super(message);
    }
}
