package br.com.Inovasys.modulos.gestaoOficina.os.mapper;

import br.com.Inovasys.modulos.gestaoOficina.cliente.mapper.ClienteMapper;
import br.com.Inovasys.modulos.gestaoOficina.empresa.mapper.EmpresaMapper;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.mapper.FuncionarioMapper;
import br.com.Inovasys.modulos.gestaoOficina.itemEstoqueOs.mapper.ItemEstoqueOSMapper;
import br.com.Inovasys.modulos.gestaoOficina.itemServicoOS.mapper.ItemServicoOSMapper;
import br.com.Inovasys.modulos.gestaoOficina.os.dto.CriarOrdemServicoDTO;
import br.com.Inovasys.modulos.gestaoOficina.os.dto.OrdemServicoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.os.entity.OrdemServico;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.mapper.VeiculoMapper;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {
                ClienteMapper.class,
                VeiculoMapper.class,
                FuncionarioMapper.class,
                EmpresaMapper.class,
                ItemServicoOSMapper.class,
                ItemEstoqueOSMapper.class
        }
)
public interface OrdemServicoMapper {

    OrdemServico toEntity(CriarOrdemServicoDTO ordemServicoDTO);
    OrdemServicoResponseDTO toResponse(OrdemServico ordemServico);

}
