package com.gft.ControleStarterMVC.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Etapa {
    MVC("MVC"),
    API("API"),
    TDD("TDD");

    private final String description;
}
