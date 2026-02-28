package br.com.Inovasys.infra.exceptions.EstoqueException;

public class EstoqueNuloException extends RuntimeException {
    public EstoqueNuloException(String message) {
        super(message);
    }
}
