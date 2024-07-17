package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

//Records são imutáveis uma vez que recebe valor ele não pode ser alterado.
public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value) {

}
