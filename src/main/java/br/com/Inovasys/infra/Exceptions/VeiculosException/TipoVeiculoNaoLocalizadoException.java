package br.com.Inovasys.infra.Exceptions.VeiculosException;

public class TipoVeiculoNaoLocalizadoException extends RuntimeException {
    public TipoVeiculoNaoLocalizadoException(String message) {
        super(message);
    }
}
