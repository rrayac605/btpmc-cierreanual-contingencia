package mx.gob.imss.cit.pmc.contingencia.tasklet;

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
import mx.gob.imss.cit.pmc.contingencia.repository.ProcessControlRepository;

@Component
@StepScope
public class FileControlTasklet implements Tasklet {

    @Autowired
    private ProcessControlRepository processControlRepository;

    @Value("#{stepExecution}")
    private StepExecution stepExecution;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Long key = stepExecution.getJobParameters().getLong(ConfrontaConstants.KEY_PARAM);
        processControlRepository.createCorrect(ProcessActionEnum.FILE_GENERATION.getDesc(), key);
        return RepeatStatus.FINISHED;
    }

}
