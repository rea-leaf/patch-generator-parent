/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-desk">patch-generator-desk</a> All rights reserved.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mozi.patch.generator.desk.Enum;

/**
 * maven pom依赖级别
 * @author aaron
 */
public enum DependLevelEnum {
    COMPILE,TEST,RUNTIME,PROVIDED,SYSTEM;
    
    private DependLevelEnum() {
        // compiled code
    }
    
}
