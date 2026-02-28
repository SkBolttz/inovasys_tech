package br.com.Inovasys.infra.exceptions.EmpresaException;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException(String message) {
        super(message);
    }
}
