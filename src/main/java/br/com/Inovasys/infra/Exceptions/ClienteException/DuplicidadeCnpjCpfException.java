package br.com.Inovasys.infra.Exceptions.ClienteException;

public class DuplicidadeCnpjCpfException extends RuntimeException {
    public DuplicidadeCnpjCpfException(String message) {
        super(message);
    }
}
