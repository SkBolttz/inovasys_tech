package br.com.Inovasys.infra.exceptions.ClienteException;

public class DuplicidadeCnpjCpfException extends RuntimeException {
    public DuplicidadeCnpjCpfException(String message) {
        super(message);
    }
}
