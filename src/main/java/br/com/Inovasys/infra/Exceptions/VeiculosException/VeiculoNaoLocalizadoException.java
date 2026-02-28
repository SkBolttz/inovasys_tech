package br.com.Inovasys.infra.Exceptions.VeiculosException;

public class VeiculoNaoLocalizadoException extends RuntimeException {
    public VeiculoNaoLocalizadoException(String message) {
        super(message);
    }
}
