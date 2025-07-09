package com.store.security.store_security.dto;

import com.store.security.store_security.entity.OrderEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackDto {

	private long id;

	private OrderEntity order;

	private String status;
}
