package mx.gob.imss.cit.pmc.contingencia.repository;

import java.util.List;

import mx.gob.imss.cit.pmc.contingencia.dto.ProcessControlDTO;

public interface ProcessControlRepository {

    void createError(String action, Long key);

    void createCorrect(String action, Long key);

    boolean validateAction(String action);

    List<ProcessControlDTO> findAllError(List<String> actions);

}
