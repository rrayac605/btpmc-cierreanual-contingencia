package mx.gob.imss.cit.pmc.contingencia.repository;

import org.springframework.data.mongodb.core.aggregation.TypedAggregation;

public interface CountRepository {

    long count(TypedAggregation<?> aggregation);

}
