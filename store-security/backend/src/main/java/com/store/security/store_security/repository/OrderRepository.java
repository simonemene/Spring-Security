package com.store.security.store_security.repository;

import com.store.security.store_security.entity.OrderEntity;
import com.store.security.store_security.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

	Optional<OrderEntity> findByUser(UserEntity user);
}
