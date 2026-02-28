package br.com.Inovasys.infra.Exceptions.EstoqueException;

public class DuplicidadeCodigoException extends RuntimeException {
    public DuplicidadeCodigoException(String message) {
        super(message);
    }
}
