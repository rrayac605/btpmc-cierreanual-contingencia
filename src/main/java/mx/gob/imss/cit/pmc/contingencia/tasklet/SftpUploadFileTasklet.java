package mx.gob.imss.cit.pmc.contingencia.tasklet;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.JSchException;

import mx.gob.imss.cit.pmc.contingencia.constants.ConfrontaConstants;
import mx.gob.imss.cit.pmc.contingencia.dto.Sftp1Properties;
import mx.gob.imss.cit.pmc.contingencia.enums.AuditActionEnum;
import mx.gob.imss.cit.pmc.contingencia.enums.ProcessActionEnum;
import mx.gob.imss.cit.pmc.contingencia.repository.FileControlRepository;
import mx.gob.imss.cit.pmc.contingencia.repository.ProcessAuditRepository;
import mx.gob.imss.cit.pmc.contingencia.sftp.SftpClient;
import mx.gob.imss.cit.pmc.contingencia.sftp.SftpConfronta;
import mx.gob.imss.cit.pmc.contingencia.utils.StringUtils;

@Component
@StepScope
public class SftpUploadFileTasklet implements Tasklet {

	private static final Logger logger = LoggerFactory.getLogger(SftpUploadFileTasklet.class);

	@Autowired
	private Sftp1Properties sftp1Properties;

	@Autowired
	private FileControlRepository fileControlRepository;

	@Autowired
	private ProcessAuditRepository processAuditRepository;

	@Value("#{stepExecution}")
	private StepExecution stepExecution;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String action = ProcessActionEnum.FILE_STORAGE.getDesc();
		String auditAction = AuditActionEnum.FILES_STORAGE.getDesc();
		Map<Long, String> sftpPathMap = ConfrontaConstants.PATH_FTP1;
		String basePath = sftp1Properties.getPath();
		SftpClient sftpClient;
//		Set<Long> keyList = fileControlRepository.findKeyListOfGeneratedFiles();
//		Set<Long> actionKeyList = fileControlRepository.findKeyListOfStoredFiles();
//		if (actionKeyList.size() > 0) {
//			keyList = keyList.stream().filter(key -> !actionKeyList.contains(key)).collect(Collectors.toSet());
//		}
		Set<Long> llavesCorrectas = new HashSet<>();
		Set<Long> exceptionKeyList = new HashSet<>();
		try {
			sftpClient = SftpClient.getSftpClient(sftp1Properties.getHost(), sftp1Properties.getPort(),
					sftp1Properties.getUser(), sftp1Properties.getPass());
		} catch (JSchException e) {
//			for (Long key : keyList) {
//				fileControlRepository.createError(action, key);
//			}
			processAuditRepository.createIncorrect(auditAction, null);
			logger.error("Error de coneccion con el sftp", e);
			return RepeatStatus.FINISHED;
		}
		for (int key : ConfrontaConstants.DEL_SUBDEL.keySet()) {
			try {
				// Agregar excepcion para EA04 y EA05
				String fullBasePath = StringUtils.buildFilePath(Long.valueOf(key), basePath, sftpPathMap);
				SftpConfronta.createBaseFolder(fullBasePath);
				logger.info("Ruta donde se depositara el archivo {}", fullBasePath);
				logger.info("Nombre del archivo {} para la key {}", StringUtils.nombreArchivo(Long.valueOf(key)), key);
				sftpClient.uploadFile(StringUtils.buildFileName(Long.valueOf(key), ConfrontaConstants.BASE_PATH_SERVER,
						ConfrontaConstants.PATH_FTP1), fullBasePath.concat(StringUtils.nombreArchivo(Long.valueOf(key))));
				llavesCorrectas.add(Long.valueOf(key));
//				fileControlRepository.createCorrect(action, key);
			} catch (Exception e) {
//				exceptionKeyList.add(key);
//				fileControlRepository.createError(action, key);
				logger.error("Error al subir el archivo para la key {}", key);
			}
		}
		
		if (exceptionKeyList.size() > 0) {
			processAuditRepository.createIncorrect(auditAction, null);
		}
		sftpClient.disconnect();
		return RepeatStatus.FINISHED;
	}

}
