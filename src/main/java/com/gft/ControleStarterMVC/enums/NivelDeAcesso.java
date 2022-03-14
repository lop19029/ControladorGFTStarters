package com.gft.ControleStarterMVC.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NivelDeAcesso {
    FULL("Full"),
    RESTRICTED("Restricted");

    private final String description;
}
