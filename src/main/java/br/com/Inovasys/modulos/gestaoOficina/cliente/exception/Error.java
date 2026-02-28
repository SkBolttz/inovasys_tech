package br.com.Inovasys.modulos.gestaoOficina.cliente.exception;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Error {
    private int status;
    private String erro;
    private String mensagem;
    private LocalDateTime timestamp;

    public Error(Integer status, String erro, String mensagem){
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
    }

}
