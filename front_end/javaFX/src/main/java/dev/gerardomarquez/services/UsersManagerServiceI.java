package dev.gerardomarquez.services;

import java.util.List;

import dev.gerardomarquez.requests.InsertUserRequest;

public interface UsersManagerServiceI {

    public List<String> insertOneUser(InsertUserRequest insertUserRequest);
}
