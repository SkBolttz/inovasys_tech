package br.com.Inovasys.infra.Exceptions;

import br.com.Inovasys.infra.Exceptions.ClienteException.ClienteNaoLocalizadoException;
import br.com.Inovasys.infra.Exceptions.ClienteException.DuplicidadeEmailClienteException;
import br.com.Inovasys.infra.Exceptions.ClienteException.DuplicidadeTelefoneClienteException;
import br.com.Inovasys.infra.Exceptions.EmpresaException.CNPJDuplicadoException;
import br.com.Inovasys.infra.Exceptions.EmpresaException.EmailDuplicadoException;
import br.com.Inovasys.infra.Exceptions.EmpresaException.EmpresaNaoLocalizadaException;
import br.com.Inovasys.infra.Exceptions.EstoqueException.DuplicidadeCodigoException;
import br.com.Inovasys.infra.Exceptions.EstoqueException.DuplicidadeDescricaoException;
import br.com.Inovasys.infra.Exceptions.EstoqueException.EstoqueNuloException;
import br.com.Inovasys.infra.Exceptions.EstoqueException.ItemEstoqueNaoLocalizadoException;
import br.com.Inovasys.infra.Exceptions.FuncionarioException.FuncionarioNaoLocalizadoException;
import br.com.Inovasys.infra.Exceptions.FuncionarioException.FuncionarioStatusException;
import br.com.Inovasys.infra.Exceptions.VeiculosException.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Exceptions para Clientes:

    @ExceptionHandler(DuplicidadeEmailClienteException.class)
    public ResponseEntity<Error> handlerEmailDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Email já cadastro em sistema.",
                        "Ocorreu um erro com o email informado, por favor tente novamente."));
    }

    @ExceptionHandler(ClienteNaoLocalizadoException.class)
    public ResponseEntity<Error> handlerClienteNaoLocalizado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Cliente não localizado em sistema.",
                        "Ocorreu um erro com o cliente informado, por favor tente novamente."));
    }

    @ExceptionHandler(DuplicidadeTelefoneClienteException.class)
    public ResponseEntity<Error> handlerTelefoneDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Telefone já cadastrado em sistema.",
                        "Ocorreu um erro com o telefone informado, por favor tente novamente."));
    }

    //----------------------------//----------------------------//----------------------------/----------------------------//

    // Exceptions para Empresas:

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<Error> handleEmailDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Email já cadastro em sistema.",
                        "Ocorreu um erro com o email informado, por favor tente novamente."));
    }

    @ExceptionHandler(EmpresaNaoLocalizadaException.class)
    public ResponseEntity<Error> handleEmpresaNaoLocalizada(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Empresa não localizada.",
                        "Ocorreu um erro com a empresa informado, por favor tente novamente."));
    }

    //----------------------------//----------------------------//----------------------------/----------------------------//

    // Exceptions para Estoque:

    @ExceptionHandler(DuplicidadeDescricaoException.class)
    public ResponseEntity<Error> handlerDescricaoDuplicidade(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Descrição já cadastrada em sistema.",
                        "Ocorreu um erro com a descrição informado, por favor tente novamente."));
    }

    @ExceptionHandler(DuplicidadeCodigoException.class)
    public ResponseEntity<Error> handlerCodigoDuplicidade(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Código já cadastrado em sistema.",
                        "Ocorreu um erro com o código informado, por favor tente novamente."));
    }

    @ExceptionHandler(ItemEstoqueNaoLocalizadoException.class)
    public ResponseEntity<Error> handlerItemEstoqueNaoLocalizado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Item estoque não localizado.",
                        "Ocorreu um erro com o item informado, por favor tente novamente."));
    }

    @ExceptionHandler(EstoqueNuloException.class)
    public ResponseEntity<Error> handlerEstoqueNulo(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Estoque atual nao pode ser negativo.",
                        "Ocorreu um erro com o item informado, por favor tente novamente."));
    }

    //----------------------------//----------------------------//----------------------------/----------------------------//

    // Exceptions para Genéricos:

    @ExceptionHandler(CNPJDuplicadoException.class)
    public ResponseEntity<Error> handlerCnpjDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "CNPJ já cadastro em sistema.",
                        "Ocorreu um erro com o CNPJ informado, por favor tente novamente."));
    }

    //----------------------------//----------------------------//----------------------------/----------------------------//

    // Exceptions para Funcionários:

    @ExceptionHandler(FuncionarioNaoLocalizadoException.class)
    public ResponseEntity<Error> handlerFuncionarioNaoLocalizado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Funcionário não localizado, tente novamente.",
                        "Ocorreu um erro com o funcionário informado, por favor tente novamente."));
    }

    @ExceptionHandler(FuncionarioStatusException.class)
    public ResponseEntity<Error> handleFuncionarioStatus(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Não foi possível alterar o status do funcionário.",
                        "Ocorreu um erro com o funcionário informado, por favor tente novamente."));
    }

    //----------------------------//----------------------------//----------------------------/----------------------------//

    // Exceptions para Veiculos:

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
