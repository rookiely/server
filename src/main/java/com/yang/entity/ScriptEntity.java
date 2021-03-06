package com.yang.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ScriptEntity {

    @Getter @Setter
    private int id;

    @Getter @Setter
    private String scriptName;

    @Getter @Setter
    private String scriptContent;
}
