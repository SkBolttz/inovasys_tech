package br.com.Inovasys.infra.exceptions.EstoqueException;

public class DuplicidadeCodigoException extends RuntimeException {
    public DuplicidadeCodigoException(String message) {
        super(message);
    }
}
