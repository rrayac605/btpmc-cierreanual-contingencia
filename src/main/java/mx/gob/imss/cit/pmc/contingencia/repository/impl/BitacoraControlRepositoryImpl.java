package mx.gob.imss.cit.pmc.contingencia.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import mx.gob.imss.cit.pmc.contingencia.dto.BitacoraControlDTO;
import mx.gob.imss.cit.pmc.contingencia.enums.BitacoraControlEnum;
import mx.gob.imss.cit.pmc.contingencia.repository.BitacoraControlRepository;
import mx.gob.imss.cit.pmc.contingencia.utils.DateUtils;
import mx.gob.imss.cit.pmc.contingencia.utils.NumberUtils;

import java.util.List;

@Repository
public class BitacoraControlRepositoryImpl implements BitacoraControlRepository{
	
	@Autowired
	MongoOperations mongoOperations;
	
	@Override
	public void createCorrect(String action, Long key) {
		
		BitacoraControlDTO bitacoraControlDTO = buildBitacoraControl(action, BitacoraControlEnum.CORRECT.getDesc(), key);
		mongoOperations.save(bitacoraControlDTO);
		
	}
	
	private BitacoraControlDTO buildBitacoraControl(String action, String control, Long key) {
		
		Integer del = NumberUtils.getDel(key);
		List<Integer> subDelList = NumberUtils.getSubDelList(key);
		
		BitacoraControlDTO bitacoraControlDTO = new BitacoraControlDTO();
		
		bitacoraControlDTO.setDel(del);
		bitacoraControlDTO.setSubDel(subDelList);
		bitacoraControlDTO.setAccion(action);
		bitacoraControlDTO.setFecEjecucion(DateUtils.getCurrentMexicoDate());
		bitacoraControlDTO.setControl(BitacoraControlEnum.CORRECT.getDesc());
		bitacoraControlDTO.setKey(key.intValue());
		
		return bitacoraControlDTO;
		
	}

}
