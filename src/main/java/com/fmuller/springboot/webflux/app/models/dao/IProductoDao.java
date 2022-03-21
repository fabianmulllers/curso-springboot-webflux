package com.fmuller.springboot.webflux.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.fmuller.springboot.webflux.app.models.documents.Producto;

public interface IProductoDao extends ReactiveMongoRepository<Producto, String>{

	
}
