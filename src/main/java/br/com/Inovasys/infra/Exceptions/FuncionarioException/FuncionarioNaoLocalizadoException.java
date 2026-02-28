package br.com.Inovasys.infra.Exceptions.FuncionarioException;

public class FuncionarioNaoLocalizadoException extends RuntimeException {
    public FuncionarioNaoLocalizadoException(String message) {
        super(message);
    }
}
