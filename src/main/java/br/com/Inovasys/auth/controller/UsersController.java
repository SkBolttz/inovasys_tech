package br.com.Inovasys.auth.controller;

import br.com.Inovasys.auth.dto.*;
import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.infra.security.TokenJWT;
import br.com.Inovasys.infra.security.TokenService;
import br.com.Inovasys.auth.service.UsersService;
import br.com.Inovasys.auth.util.ObterUsuarioLogado;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UsersController {

    private final UsersService usersService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UsersController(UsersService usersService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.cpf(), loginDTO.senha());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        Users user = (Users) authentication.getPrincipal();
        String token = tokenService.gerarToken(user);

        System.out.println(ObterUsuarioLogado.obterCpfUsuarioLogado());

        return ResponseEntity.ok(new TokenJWT(token));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UserResponseDTO> cadastro(@RequestBody @Valid CadastroDTO cadastroDTO){
        return ResponseEntity.status(201).body(usersService.cadastrarUsuario(cadastroDTO.cpf(), cadastroDTO.email(), null));
    }

    @PutMapping("/atualizar-informacoes")
    public ResponseEntity<UserResponseDTO> atualizarInformacoes(@RequestBody @Valid AtualizarInformacoes atualizarInformacoes){
        return ResponseEntity.ok(usersService.atualizarInformacoes(atualizarInformacoes));
    }

    @GetMapping("/buscar")
    public ResponseEntity<UserResponseDTO> buscarUser(){
        return ResponseEntity.ok(usersService.buscarUsuario());
    }

}
