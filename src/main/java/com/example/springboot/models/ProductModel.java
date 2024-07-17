package com.example.springboot.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "TB_PRODUCTS")
/* O RepresentationModel<ProductModel> está sendo utilizado para que seja possível utilizar dos Hateoas, que são usados para gerar HiperLinks(HiperMidias) dentro da API Rest,
 para garantir uma navegabilidade para os recursos (porém seu uso depende complexidade pode apresentar limitações)
 OBS- HateOas foram implementados apenas para aprendizado e teste na API em questão.
 O <ProductModel> foi passado assim para saber qual será a entidade que terá a representação. De modo que quando for utilizar o ProductModel internamente ele terá métodos
 para auxiliar na crição dos links de navegação. */
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    /* Adicionados os parametros @Id e @GeneratedValue, para que ao criar a tabela ele irá gerar sempre um ID identificador para cada
    produto, sem precisar ficar gerando um valor, de modo que seja gerado automaticamente, como funciona o Identity no tipo de coluna
    no SQL Server.*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Identificadores distribuidos(UUID), que evitam conflitos de que seja gerado um ID repetido dentro do projeto geral em arquiteturas distribuidas.
    private UUID idProduct;
    private String name;
    private BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }
}
