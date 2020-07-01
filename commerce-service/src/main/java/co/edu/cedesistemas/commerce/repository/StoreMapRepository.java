package co.edu.cedesistemas.commerce.repository;

import co.edu.cedesistemas.commerce.model.Store;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class StoreMapRepository implements StoreRepository {
    private final Map<String, Store> dataMap;
    
    public StoreMapRepository(Map<String,Store> dataMap) {
		this.dataMap = dataMap;
	}

    @Override
    public List<Store> findByName(String name) {
        return dataMap.values().stream()
                .filter(s -> s.getName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Store> findByType(Store.Type type) {
        return dataMap.values().stream()
                .filter(s -> s.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public <S extends Store> S save(S entity) {
        dataMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Store findById(String s) {
        return dataMap.get(s);
    }

    @Override
    public void remove(String s) {
        dataMap.remove(s);
    }

    @Override
    public Iterable<Store> findAll() {
        return dataMap.values();
    }
}
