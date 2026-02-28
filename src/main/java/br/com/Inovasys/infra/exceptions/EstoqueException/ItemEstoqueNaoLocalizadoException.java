package br.com.Inovasys.infra.exceptions.EstoqueException;

public class ItemEstoqueNaoLocalizadoException extends RuntimeException {
    public ItemEstoqueNaoLocalizadoException(String message) {
        super(message);
    }
}
