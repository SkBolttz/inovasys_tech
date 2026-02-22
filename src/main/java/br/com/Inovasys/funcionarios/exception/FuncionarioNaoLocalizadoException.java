package br.com.Inovasys.funcionarios.exception;

public class FuncionarioNaoLocalizadoException extends RuntimeException {
    public FuncionarioNaoLocalizadoException(String message) {
        super(message);
    }
}
