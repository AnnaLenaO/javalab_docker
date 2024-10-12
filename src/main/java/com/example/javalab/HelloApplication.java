package com.example.javalab;

import com.example.javalab.exceptionmapper.IllegalCategoryExceptionMapper;
import com.example.javalab.exceptionmapper.IllegalRatingExceptionMapper;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class HelloApplication extends Application {

//    @Override
//    public Set<Class<?>> getClasses() {
//        Set<Class<?>> classes = new HashSet<>();
//        classes.add(IllegalRatingExceptionMapper.class);
//        return classes;
//    }

//    @Override
//    public Set<Object> getSingletons() {
//        Set<Object> singletons = new HashSet<>();
//
//        singletons.add(new IllegalRatingExceptionMapper());
//        singletons.add(new IllegalCategoryExceptionMapper());
//
//        return singletons;
//    }
}