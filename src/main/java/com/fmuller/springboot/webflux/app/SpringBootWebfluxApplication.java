package com.fmuller.springboot.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.fmuller.springboot.webflux.app.models.dao.IProductoDao;
import com.fmuller.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		mongoTemplate.dropCollection("productos").subscribe();
		
		Flux.just(new Producto("TV Panasonic Pantalla LCD",456.34),
				new Producto("Sony Camara HD",177.34),
				new Producto("Apple Ipod",45.43),
				new Producto("Hewlett Packard multifuncional",300.23),
				new Producto("Bianchi bici",233.42),
				new Producto("Mica Comoda Cajones",343.34),
				new Producto("Sony Bravia",567.34))
		.flatMap(producto -> {
			producto.setCreateAt(new Date());
			return productoDao.save(producto);
		})
		
		.subscribe(producto -> log.info("Insert:" + producto.getId()+ " "+ producto.getNombre()));

		
	}

}
