package br.com.Inovasys.infra.exceptions.ServicoException;

public class ServicoDuplicadoException extends RuntimeException {
    public ServicoDuplicadoException(String message) {
        super(message);
    }
}
