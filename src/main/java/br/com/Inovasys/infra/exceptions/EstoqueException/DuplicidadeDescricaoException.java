package br.com.Inovasys.infra.exceptions.EstoqueException;

public class DuplicidadeDescricaoException extends RuntimeException {
    public DuplicidadeDescricaoException(String message) {
        super(message);
    }
}
