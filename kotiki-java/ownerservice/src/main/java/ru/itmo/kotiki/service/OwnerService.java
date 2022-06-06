package ru.itmo.kotiki.service;

import ru.itmo.kotiki.dto.OwnerDto;
import ru.itmo.kotiki.tool.ServiceException;

import java.util.List;

public interface OwnerService {
	OwnerDto add(OwnerDto owner);

	List<OwnerDto> getAll();

	OwnerDto getById(long id) throws ServiceException;

	void update(OwnerDto owner) throws ServiceException;

	void remove(long id) throws ServiceException;
}