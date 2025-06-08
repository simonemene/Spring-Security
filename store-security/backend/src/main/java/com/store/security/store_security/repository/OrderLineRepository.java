package com.store.security.store_security.repository;

import com.store.security.store_security.entity.OrderLineEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends CrudRepository<OrderLineEntity, Long> {
}
