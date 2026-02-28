package br.com.Inovasys.infra.exceptions.VeiculosException;

public class VeiculoNaoLocalizadoException extends RuntimeException {
    public VeiculoNaoLocalizadoException(String message) {
        super(message);
    }
}
