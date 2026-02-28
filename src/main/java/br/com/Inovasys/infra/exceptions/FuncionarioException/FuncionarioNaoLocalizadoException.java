package br.com.Inovasys.infra.exceptions.FuncionarioException;

public class FuncionarioNaoLocalizadoException extends RuntimeException {
    public FuncionarioNaoLocalizadoException(String message) {
        super(message);
    }
}
