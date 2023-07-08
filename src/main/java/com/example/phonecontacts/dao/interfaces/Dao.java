package com.example.phonecontacts.dao.interfaces;

import java.util.List;
import java.util.Optional;

public interface Dao<E> {

    E create(E entity);

    E update(E entity,Long entityId);

    Optional<E> getById(Long id);

    String delete(Long entityId);

    List<E> findAll();
}
