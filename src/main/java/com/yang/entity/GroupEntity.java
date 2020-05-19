package com.yang.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class GroupEntity {

    @Getter @Setter
    private int id;

    @Getter @Setter
    private String host;

    @Getter @Setter
    private String system;

    @Getter @Setter
    private int port;

    @Getter @Setter
    private String group;
}
