package br.com.Inovasys.infra.exceptions.ServicoException;

public class ServicoNaoLocalizadoException extends RuntimeException {
    public ServicoNaoLocalizadoException(String message) {
        super(message);
    }
}
