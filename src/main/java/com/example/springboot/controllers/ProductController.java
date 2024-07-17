package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/* NESTE EXEMPLO FORAM UTILIZADOS O PRÓPRIO REPOSITORY DENTRO DO CONTROLLER, COMO MÉTODO DE ESTUDO CRIAR A CLASSE SERVICE POR BOA PRÁTICA. */


/* Estereótipo para indicar ao Spring que esse será um bin que terá uma implementação de API Rest.  */
@RestController
public class ProductController {
    /* Foi utilizado o @Autowired no lugar do constructor, porém poderia ser usado o constructor sem problemas. */
    @Autowired
    ProductRepository productRepository;

    // 1º método que será utilizado para salvar os produtos, utilizando uma chamada Post para a API.
    /* O @Valid precisa ser utilizado para que sejam realizados as validações passadas dentro do RecordDto,
       como exemplo para não ser inserido no banco valor null.

       Ao inicializar um método, precisa iniciar o Model, pois será salvo dentro da base de dados o Model e não o DTO.
       O DTO é utilizado apenas para receber o que está vindo no corpo da requisição, no JSON para ser feita a serialização
       do mesmo para um objeto Java, fazer as validações iniciais, mas depois de ser feito o passo a passo inicial precisa
       transformar(converter) o DTO para Model, para que nosso Model seja salvo na base de dados.
     */

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){
        var productModel = new ProductModel();
        /* Bean Utils é um dos métodos que podem ser utilizados para converter o DTO para Model,
         recebendo o que vai ser convertido e o tipo que será convertido.
         */
        BeanUtils.copyProperties(productRecordDto, productModel);
        return  ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    // 2º Método que será utilizado para retornar ao usuário a lista com todos os produtos cadastrados no banco de dados.
    /* MÉTODO CRIADO SEM O USO DE HATEOAS
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        return  ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }
    */
    /* Método ajustado utilizando o HATEOAS, de modo a ele pegar a lista de produtos, gerando um link para acessar as informações de um produto especifico
     acessando assim o método getOneProducts. */
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        List<ProductModel> productsList = productRepository.findAll();
        if(!productsList.isEmpty()){
            for(ProductModel product : productsList){
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProducts(id)).withSelfRel());
            }
        }
        return  ResponseEntity.status(HttpStatus.OK).body(productsList);
    }

    // 3º Método que será utilizado para retornar ao usuário um item especifico do banco de dados. ## SEM USO DE HATEOAS
    /*
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProducts(@PathVariable(value="id") UUID id){
        Optional<ProductModel> productO = productRepository.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productO.get());
    }
    */
    // MÉTODO COM HATEOAS
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProducts(@PathVariable(value="id") UUID id){
        Optional<ProductModel> productO = productRepository.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productO.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(productO.get());
    }

    // 4º Método que será utilizado alterar um respectivo item dentro da base de dados.
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto){
        Optional<ProductModel> productO = productRepository.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        var productModel = productO.get();
        BeanUtils.copyProperties(productRecordDto,productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    // 5º Método delete que será utilizado para apagar um respectivo item de dentro da base de dados.
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id){
        Optional<ProductModel> productO = productRepository.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productRepository.delete(productO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }
}
