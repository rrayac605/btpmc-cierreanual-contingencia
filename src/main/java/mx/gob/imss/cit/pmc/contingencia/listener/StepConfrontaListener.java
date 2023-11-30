package mx.gob.imss.cit.pmc.contingencia.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.pmc.contingencia.constants.ConfrontaConstants;

@Component
@StepScope
public class StepConfrontaListener implements StepExecutionListener {

    private static int fileValidationCount = 1;

    private static int uploadFileCount = 1;

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String stepName = stepExecution.getStepName();
        if (ConfrontaConstants.FILE_VALIDATION_STEP.equals(stepName)) {
            stepName = ConfrontaConstants.FILE_VALIDATION_EQUIVALENT.get(fileValidationCount);
            fileValidationCount++;
            resetCount();
        }
        if (ConfrontaConstants.SFTP_UPLOAD_FILE_STEP.equals(stepName)) {
            stepName = ConfrontaConstants.UPLOAD_FILE_EQUIVALENT.get(uploadFileCount);
            uploadFileCount++;
            resetUploadCount();
        }
        stepExecution.getJobExecution().getExecutionContext().putString(ConfrontaConstants.ACTION_PARAM,
                ConfrontaConstants.FROM_STEP_ACTION_NAME.get(stepName));
        stepExecution.getJobExecution().getExecutionContext().putString(ConfrontaConstants.ACTION_AUDIT_PARAM,
                ConfrontaConstants.FROM_STEP_ACTION_AUDIT_NAME.get(stepName));
        stepExecution.getJobExecution().getExecutionContext().putString(ConfrontaConstants.PAST_STEP_PARAM,
                stepName);
        return ExitStatus.COMPLETED;
    }

    private void resetCount() {
        if (fileValidationCount > 3) {
            fileValidationCount = 1;
        }
    }

    private void resetUploadCount() {
        if (uploadFileCount > 2) {
            uploadFileCount = 1;
        }
    }


}
