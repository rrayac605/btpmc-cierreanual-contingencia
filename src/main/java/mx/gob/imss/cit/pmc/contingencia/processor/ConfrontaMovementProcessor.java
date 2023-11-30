package mx.gob.imss.cit.pmc.contingencia.processor;

import lombok.SneakyThrows;
import mx.gob.imss.cit.pmc.contingencia.constants.ConfrontaConstants;
import mx.gob.imss.cit.pmc.contingencia.dto.AseguradoDTO;
import mx.gob.imss.cit.pmc.contingencia.dto.IncapacidadDTO;
import mx.gob.imss.cit.pmc.contingencia.dto.MovementDTO;
import mx.gob.imss.cit.pmc.contingencia.dto.PatronDTO;
import mx.gob.imss.cit.pmc.contingencia.utils.DateUtils;
import mx.gob.imss.cit.pmc.contingencia.utils.NumberUtils;
import mx.gob.imss.cit.pmc.contingencia.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@StepScope
public class ConfrontaMovementProcessor implements ItemProcessor<MovementDTO, String> {

    private static final Logger logger = LoggerFactory.getLogger(ConfrontaMovementProcessor.class);
    
    @Value("#{stepExecution}")
    private StepExecution stepExecution;
    
        
    @SneakyThrows
    @Override
    public String process(MovementDTO movementDTO) {
    	//Agregar exception para obtener información
    	Long key = stepExecution.getJobParameters().getLong(ConfrontaConstants.KEY_PARAM);
    	boolean esReproceso = Boolean.TRUE.toString().equals(stepExecution.getJobParameters().getString(
    			ConfrontaConstants.REPROCESS_FLAG));
        AseguradoDTO asegurado = movementDTO.getAseguradoDTO();
        PatronDTO patron = movementDTO.getPatronDTO();
        IncapacidadDTO incapacidad = movementDTO.getIncapacidadDTO();
        return StringUtils.safeSubString(asegurado.getNumNss(), ConfrontaConstants.TEN)
                .concat(StringUtils.safeSubString(patron.getRefRegistroPatronal(), ConfrontaConstants.TEN))
                .concat(NumberUtils.processConsequence(
                        incapacidad.getCveConsecuencia(), incapacidad.getNumDiasSubsidiados()).toString()
                )
                .concat(DateUtils.getFileFormattedDate(
                        DateUtils.orElse(incapacidad.getFecInicio(), incapacidad.getFecAccidente())
                ))
                .concat(StringUtils.safeAddZero(incapacidad.getNumDiasSubsidiados(), 4))
                .concat(DateUtils.getFileFormattedDate(
                        DateUtils.orElse(incapacidad.getFecFin(), incapacidad.getFecIniPension(), incapacidad.getFecAltaIncapacidad())
                ))
                .concat(NumberUtils.safeValidateInteger(incapacidad.getCveTipoRiesgo()).toString())
                .concat(StringUtils.safeAddZero(NumberUtils.safetyParseBigDecimal(incapacidad.getPorPorcentajeIncapacidad()), 3))
                .concat(StringUtils.concatFullNameFile(asegurado.getNomAsegurado(), asegurado.getRefPrimerApellido(), asegurado.getRefSegundoApellido()))
                .concat("0000000")
                .concat(StringUtils.safeValidateCurp(asegurado.getRefCurp()))
                .concat("0000000000000000000000");
    }
}
