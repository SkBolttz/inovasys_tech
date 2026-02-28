package br.com.Inovasys.infra.Exceptions.EmpresaException;

public class CNPJDuplicadoException extends RuntimeException {
    public CNPJDuplicadoException(String message) {
        super(message);
    }
}
