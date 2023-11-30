package mx.gob.imss.cit.pmc.contingencia.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.pmc.contingencia.constants.ConfrontaConstants;
import mx.gob.imss.cit.pmc.contingencia.enums.ProcessActionEnum;
import mx.gob.imss.cit.pmc.contingencia.repository.FileControlRepository;
import mx.gob.imss.cit.pmc.contingencia.utils.ExitStatusConfronta;

@Component
@StepScope
public class FileValidationTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(FileValidationTasklet.class);

    @Value("#{stepExecution}")
    private StepExecution stepExecution;

    @Autowired
    private FileControlRepository fileControlRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		boolean isValid = fileControlRepository.validate();
        if (!isValid) {
            stepExecution.getJobExecution().getExecutionContext()
                    .putString(ConfrontaConstants.IS_TERMINATED_ONLY, Boolean.TRUE.toString());
        }
        stepExecution.setExitStatus(isValid ? ExitStatus.COMPLETED : ExitStatusConfronta.GENERATION_FILE_FAILED);
        return RepeatStatus.FINISHED;
    }
}
