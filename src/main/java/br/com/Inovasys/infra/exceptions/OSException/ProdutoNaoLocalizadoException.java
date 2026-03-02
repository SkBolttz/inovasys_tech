package br.com.Inovasys.infra.exceptions.OSException;

public class ProdutoNaoLocalizadoException extends RuntimeException {
    public ProdutoNaoLocalizadoException(String message) {
        super(message);
    }
}
