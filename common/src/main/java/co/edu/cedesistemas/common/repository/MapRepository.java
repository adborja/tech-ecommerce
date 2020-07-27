package co.edu.cedesistemas.common.repository;

import co.edu.cedesistemas.common.model.Entity;

public interface MapRepository<T extends Entity<ID>, ID> extends Repository<T, ID> {

}