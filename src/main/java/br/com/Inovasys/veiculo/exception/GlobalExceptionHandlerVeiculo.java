package br.com.Inovasys.veiculo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerVeiculo {

    @ExceptionHandler(DuplicidadeMarcaException.class)
    public ResponseEntity<Error> handlerMarcaDuplicada(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Marca já cadastro em sistema.",
                        "Ocorreu um erro com a marca informada, por favor tente novamente."));
    }

    @ExceptionHandler(MarcaNaoLocalizadaException.class)
    public ResponseEntity<Error> handlerMarcaNaoLocalizada(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Marca não localizada em sistema.",
                        "Ocorreu um erro com a marca informada, por favor tente novamente."));
    }

    @ExceptionHandler(DuplicidadeModeloException.class)
    public ResponseEntity<Error> handlerDuplicidadeModelo(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Modelo já cadastrado em sistema.",
                        "Ocorreu um erro com o modelo informado, por favor tente novamente."));
    }

    @ExceptionHandler(ModeloNaoLocalizadoException.class)
    public ResponseEntity<Error> handlerModeloNaoLocalizado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Modelo não localizado em sistema.",
                        "Ocorreu um erro com o modelo informado, por favor tente novamente."));
    }

    @ExceptionHandler(TipoCombustivelNaoLocalizadoException.class)
    public ResponseEntity<Error> handlerTipoCombustivelNaoLocalizado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Tipo de combustível não localizado em sistema..",
                        "Ocorreu um erro com o tipo de combustível informado, por favor tente novamente."));
    }

    @ExceptionHandler(DuplicidadeTipoCombustivelException.class)
    public ResponseEntity<Error> handlerTipoCombustivelDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Tipo de combustível já cadastrado em sistema..",
                        "Ocorreu um erro com o tipo de combustível informado, por favor tente novamente."));
    }

    @ExceptionHandler(TipoVeiculoNaoLocalizadoException.class)
    public ResponseEntity<Error> handlerTipoVeiculoNaoLocalizado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Tipo de veículo não localizado em sistema..",
                        "Ocorreu um erro com o tipo de veículo informado, por favor tente novamente."));
    }

    @ExceptionHandler(DuplicidadeTipoVeiculoException.class)
    public ResponseEntity<Error> handlerTipoVeiculoDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Tipo de veículo já cadastrado em sistema..",
                        "Ocorreu um erro com o tipo de veículo informado, por favor tente novamente."));
    }

    @ExceptionHandler(DuplicidadeVeiculoException.class)
    public ResponseEntity<Error> handlerVeiculoDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Veículo já cadastrado em sistema..",
                        "Ocorreu um erro com o veículo informado, por favor tente novamente."));
    }

    @ExceptionHandler(VeiculoNaoLocalizadoException.class)
    public ResponseEntity<Error> handlerVeiculoNaoLocalizado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Veículo não localizado em sistema..",
                        "Ocorreu um erro com o veículo informado, por favor tente novamente."));
    }
}