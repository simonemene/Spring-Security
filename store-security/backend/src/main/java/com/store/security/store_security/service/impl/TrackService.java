package com.store.security.store_security.service.impl;

import com.store.security.store_security.dto.TrackDto;
import com.store.security.store_security.entity.TrackEntity;
import com.store.security.store_security.exceptions.TrackException;
import com.store.security.store_security.mapper.TrackMapper;
import com.store.security.store_security.repository.TrackRepository;
import com.store.security.store_security.service.ITrackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TrackService implements ITrackService {

	private final TrackRepository trackRepository;

	private final TrackMapper trackMapper;

	@Override
	public TrackDto getTrackByOrder(Long idOrder) {
		TrackEntity trackEntity = trackRepository.findByOrder_Id(idOrder);
		if(trackEntity.getId() <= 0)
		{
			throw new TrackException(String.format("[ORDER: %s] Track not found",idOrder));
		}
		return trackMapper.toDto(trackEntity);
	}

	@Override
	public TrackDto setTrack(TrackDto trackDto) {
		TrackEntity trackEntity = trackRepository.save(trackMapper.toEntity(trackDto));
		if(trackEntity.getId() <= 0)
		{
			throw new TrackException(String.format("[ORDER: %s] Track not saved",trackDto.getOrder().getId()));
		}
		return trackMapper.toDto(trackEntity);
	}
}
