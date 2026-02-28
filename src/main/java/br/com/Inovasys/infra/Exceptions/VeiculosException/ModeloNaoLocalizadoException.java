package br.com.Inovasys.infra.Exceptions.VeiculosException;

public class ModeloNaoLocalizadoException extends RuntimeException {
    public ModeloNaoLocalizadoException(String message) {
        super(message);
    }
}
