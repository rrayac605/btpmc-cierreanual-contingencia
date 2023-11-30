package mx.gob.imss.cit.pmc.contingencia.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.cit.pmc.contingencia.dto.BackupConfrontaDTO;
import mx.gob.imss.cit.pmc.contingencia.enums.BackupFieldsEnum;
import mx.gob.imss.cit.pmc.contingencia.repository.BackupRepository;

@Repository
public class BackupRepositoryImpl implements BackupRepository {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void deleteByKey(Long key) {
        Query query = new Query(Criteria.where(BackupFieldsEnum.KEY.getDesc()).is(key));
        mongoOperations.remove(query, BackupConfrontaDTO.class);
    }

}
