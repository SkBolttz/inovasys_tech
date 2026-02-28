package br.com.Inovasys.infra.exceptions.EmpresaException;

public class CNPJDuplicadoException extends RuntimeException {
    public CNPJDuplicadoException(String message) {
        super(message);
    }
}
