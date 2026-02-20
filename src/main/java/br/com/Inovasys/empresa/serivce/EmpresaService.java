package br.com.Inovasys.empresa.serivce;

import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.exception.EmailDuplicadoException;
import br.com.Inovasys.auth.exception.UsuarioNaoLocalizadoException;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.auth.util.ObterUsuarioLogado;
import br.com.Inovasys.empresa.dto.empresa.CadastrarEmpresaDTO;
import br.com.Inovasys.empresa.dto.empresa.EmpresaResponseDTO;
import br.com.Inovasys.empresa.entity.Empresa;
import br.com.Inovasys.empresa.exception.CNPJDuplicadoException;
import br.com.Inovasys.empresa.mapper.EmpresaMapper;
import br.com.Inovasys.empresa.repository.EmpresaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;
    private final UsersRepository usersRepository;

    public EmpresaService(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper, UsersRepository usersRepository){
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
        this.usersRepository = usersRepository;
    }

    public EmpresaResponseDTO cadastrarEmpresa(@Valid CadastrarEmpresaDTO cadastrarEmpresaDTO) {
        validarDuplicidades(cadastrarEmpresaDTO.email(), cadastrarEmpresaDTO.cnpj());

        Empresa empresa = empresaMapper.toEntity(cadastrarEmpresaDTO);
        Users user = usersRepository.findByCpf(ObterUsuarioLogado.obterCpfUsuarioLogado()).orElseThrow(
                () -> new UsuarioNaoLocalizadoException("Usuário não localizado."));

        List<Users> users = new ArrayList<>(List.of());
        users.add(user);

        empresa.getEndereco().setCep(cadastrarEmpresaDTO.endereco().cep().replaceAll("\\D", ""));
        empresa.setUsuarios(users);
        empresa.setAbertura(parseData(cadastrarEmpresaDTO.dataAbertura()));
        empresa.setTelefone(cadastrarEmpresaDTO.telefone().replaceAll("\\D", ""));


        empresaRepository.save(empresa);

        user.setEmpresa(empresa);
        usersRepository.save(user);

        return empresaMapper.toResponse(empresa);
    }


    private void validarDuplicidades(String cnpj, String email){
        if(empresaRepository.existsByCnpj(cnpj)){
            throw new CNPJDuplicadoException("CNPJ já cadastrado em sistema, tente novamente.");
        }
        if(empresaRepository.existsByEmail(email)){
            throw new EmailDuplicadoException("Email já cadastrad em sistema.");
        }
    }

    private LocalDate parseData(String data) {
        if (data == null || data.isBlank()) return null;
        return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
