package br.com.Inovasys.infra.Exceptions.EstoqueException;

public class EstoqueNuloException extends RuntimeException {
    public EstoqueNuloException(String message) {
        super(message);
    }
}
