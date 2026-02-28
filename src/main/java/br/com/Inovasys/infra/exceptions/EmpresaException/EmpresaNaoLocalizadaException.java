package br.com.Inovasys.infra.exceptions.EmpresaException;

public class EmpresaNaoLocalizadaException extends RuntimeException {
    public EmpresaNaoLocalizadaException(String message) {
        super(message);
    }
}
