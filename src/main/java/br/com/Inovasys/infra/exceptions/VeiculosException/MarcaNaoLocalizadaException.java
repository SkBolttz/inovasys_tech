package br.com.Inovasys.infra.exceptions.VeiculosException;

public class MarcaNaoLocalizadaException extends RuntimeException {
    public MarcaNaoLocalizadaException(String message) {
        super(message);
    }
}
