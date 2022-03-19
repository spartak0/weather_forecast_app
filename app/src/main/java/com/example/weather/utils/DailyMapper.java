package com.example.weather.utils;

public interface DailyMapper<Domain, Dto>{
    public Domain toDomain(Dto dto, int day);
    public Dto fromDomain(Domain domain);
}
