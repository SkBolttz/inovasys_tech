package br.com.Inovasys.infra.Exceptions.EmpresaException;

public class EmpresaNaoLocalizadaException extends RuntimeException {
    public EmpresaNaoLocalizadaException(String message) {
        super(message);
    }
}
