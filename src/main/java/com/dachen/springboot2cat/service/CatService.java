package com.dachen.springboot2cat.service;

import com.dachen.aop.CatAnnotation;
import org.springframework.stereotype.Service;

@Service
public class CatService {

    @CatAnnotation
    public String testMethod() {
        return "test";
    }

}
