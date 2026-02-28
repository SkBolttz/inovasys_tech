package br.com.Inovasys.infra.exceptions.VeiculosException;

public class TipoVeiculoNaoLocalizadoException extends RuntimeException {
    public TipoVeiculoNaoLocalizadoException(String message) {
        super(message);
    }
}
