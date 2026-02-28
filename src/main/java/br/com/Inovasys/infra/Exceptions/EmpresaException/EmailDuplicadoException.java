package br.com.Inovasys.infra.Exceptions.EmpresaException;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException(String message) {
        super(message);
    }
}
