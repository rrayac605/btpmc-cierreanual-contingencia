package mx.gob.imss.cit.pmc.contingencia.repository;

import java.util.List;
import java.util.Optional;

import mx.gob.imss.cit.pmc.contingencia.dto.ParameterDTO;

public interface ParameterRepository {

    Optional<ParameterDTO<String>> findOneByCve(String cve);

    Optional<ParameterDTO<List<String>>> findListByCve(String cve);

}
