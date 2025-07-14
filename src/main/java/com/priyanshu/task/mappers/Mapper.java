package com.priyanshu.task.mappers;

public interface Mapper<T, K> {

    T fromDto(K k);
    K toDto(T t);
}
