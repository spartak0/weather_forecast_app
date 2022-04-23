package com.example.weather.utils;

public interface Mapper<Domain, Dto> {
    public Domain toDomain(Dto dto);
    public Dto fromDomain(Domain domain);
}