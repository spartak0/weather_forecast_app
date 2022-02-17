package com.example.weather.utils;

import java.util.Map;

public interface Mapper<Domain, Dto> {
    public Domain toDomain(Dto dto);
    public Dto fromDomain(Domain domain);
}