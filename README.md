#Introducción
BAtch encargado de procesar los datos de las casuísticas alojadas en mongodb
y enviarlas en formato de archivo a dos sftp.

#Variables de entorno

============== AMBIENTE DE QA ==============

Para que este batch pueda ser ejecutado deberán de existir las siguietnes variables de entorno en el SO annfitrión donde se ejecute el jar

- authenticationdatabaseMongo	PMCQA01
- confrontaBatchExecute	0 35 19 9 8 *
- confrontaBatchReprocess	0 55,45 20 9 8 *
- databaseMongo	PMCQA01
- fileLogBatchConfronta	/weblogic/pmc/logs/btpmc-contingencia.log
- finalSftpHost	11.254.170.45
- finalSftpPass	SftpUs3r.&,
- finalSftpPath	/opt/OD.DE/desarrollo
- finalSftpPort	22
- finalSftpUser	sftpuser
- hostMongo	10.100.8.78
- portContingenciaBatch	9022
- portMongo	27017
- pwMongoCifras	pmcmodifica0
- sftpHost	10.100.6.76
- sftpPass	Passw0rd
- sftpPath	/home/usr_pmc/archivoscierreanual/contingencia/{year}
- sftpPort	22
- sftpUser	root
- usrMongoCifras	pmcmodifica

============== AMBIENTE DE UAT ==============

Para que este batch pueda ser ejecutado deberán de existir las siguietnes variables de entorno en el SO annfitrión donde se ejecute el jar

- portConfrontaBatch: 9018
- authenticationdatabaseMongo: PMCUAT01
- usrMongoCifras: pmcmodifica
- pwMongoCifras: pmcmodifica0
- databaseMongo: PMCUAT01
- portContingenciaBatch	9022
- portMongo: 27017
- hostMongo: 10.100.8.80
- fileLogBatchConfronta: /weblogic/pmc/logs/btpmc-confronta.log
- confrontaBatchExecute: 0 35 19 9 8 *
- confrontaBatchReprocess: 0 55,45 20 9 8 *
- sftpHost: 10.100.6.98
- sftpPort: 22
- sftpUser: usr_pmc
- sftpPass: Pmc.Usr.&
- finalSftpHost: 11.254.170.45
- finalSftpPort: 22
- finalSftpUser: sftpuser
- finalSftpPass: SftpUs3r.&,
- fileLogBatchConfronta	/weblogic/pmc/logs/btpmc-contingencia.log
- sftpPath	/home/usr_pmc/archivoscierreanual/contingencia/{year}



============== AMBIENTE DE PROD ==============

Para que este microservicio pueda ser ejecutado deberán de existir las siguietnes variables de entorno en el SO annfitrión donde se ejecute el jar

- portConfrontaBatch: Pendiente
- authenticationdatabaseMongo: Pendiente
- usrMongoCifras: Pendiente
- pwMongoCifras: Pendiente
- databaseMongo: Pendiente
- portMongo: Pendiente
- hostMongo: Pendiente
- fileLogBatchConfronta: Pendiente
- confrontaBatchExecute: Pendiente
- confrontaBatchReprocess: Pendiente
- sftpHost: Pendiente
- sftpPort: Pendiente
- sftpUser: Pendiente
- sftpPass: Pendiente
- finalSftpHost: Pendiente
- finalSftpPort: Pendiente
- finalSftpUser: Pendiente
- finalSftpPass: Pendiente