package br.com.Inovasys.infra.Exceptions.EstoqueException;

public class DuplicidadeDescricaoException extends RuntimeException {
    public DuplicidadeDescricaoException(String message) {
        super(message);
    }
}
