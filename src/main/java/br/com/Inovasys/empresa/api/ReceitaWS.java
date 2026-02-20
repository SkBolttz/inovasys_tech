package br.com.Inovasys.empresa.api;

import br.com.Inovasys.auth.util.ObterUsuarioLogado;
import br.com.Inovasys.empresa.dto.empresa.EmpresaResponseDTO;
import br.com.Inovasys.empresa.dto.empresa.ReceitaEmpresaDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ReceitaWS {

    private final WebClient webClient;

    public ReceitaWS(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://www.receitaws.com.br/v1/cnpj")
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0")
                .build();
    }

    public ReceitaEmpresaDTO buscarCnpj(String cnpj) {
        return webClient.get()
                .uri("/{cnpj}", cnpj)
                .retrieve()
                .bodyToMono(ReceitaEmpresaDTO.class)
                .block();
    }
}
