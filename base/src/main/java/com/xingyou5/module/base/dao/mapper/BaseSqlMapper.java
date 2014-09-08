package com.xingyou5.module.base.dao.mapper;


public interface BaseSqlMapper<T> {
	
	public int add(T t);

	public int del(Integer id);
	
	public int remove(T t);

	public int update(T t);
	
	public int edit(T t);

	public T get(Integer id);
	
	public T get(T t);
}
