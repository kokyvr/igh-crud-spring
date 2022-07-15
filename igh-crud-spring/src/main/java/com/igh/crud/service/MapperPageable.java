package com.igh.crud.service;

import java.util.Map;

import org.springframework.data.domain.Page;

public interface MapperPageable<T> {

	public Map<String, Object> mapperPageable(Page<T> mapper);
}
