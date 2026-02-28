package br.com.Inovasys.infra.Exceptions.EstoqueException;

public class ItemEstoqueNaoLocalizadoException extends RuntimeException {
    public ItemEstoqueNaoLocalizadoException(String message) {
        super(message);
    }
}
