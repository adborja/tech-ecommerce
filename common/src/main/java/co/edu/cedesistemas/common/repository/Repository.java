package co.edu.cedesistemas.common.repository;

@org.springframework.stereotype.Repository
public interface Repository<T, ID> {
    <S extends T> S save(S entity);
    T findById(ID id);
    void remove(ID id);
    Iterable<T> findAll();
}
