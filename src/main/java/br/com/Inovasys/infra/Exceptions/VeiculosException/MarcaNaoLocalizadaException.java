package br.com.Inovasys.infra.Exceptions.VeiculosException;

public class MarcaNaoLocalizadaException extends RuntimeException {
    public MarcaNaoLocalizadaException(String message) {
        super(message);
    }
}
