package com.example.springboot.repositories;


import com.example.springboot.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/* Estereótipo "@Repository" utilizado para indicar para o Spring que a interface utilizada será um BIN gerenciado por ele do qual
   terá transações com o banco de dados. Porém nesse caso em especifico não seria necessário, pois já está sendo utilizado o
   JpaRepository, onde o mesmo já irá indicar ao spring que essa interface será um Repository e que terá transações com o banco de dados. */
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

}
