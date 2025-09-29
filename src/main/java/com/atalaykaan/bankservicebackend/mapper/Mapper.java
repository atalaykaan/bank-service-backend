package com.atalaykaan.bankservicebackend.mapper;

public interface Mapper<A,B> {

    A fromDTO(B b);

    B toDTO(A a);
}
