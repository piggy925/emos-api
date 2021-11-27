package com.mumu.emos.api.service;

import java.util.HashMap;
import java.util.Set;

public interface UserService {
    Integer login(HashMap param);

    Set<String> searchUserPermissions(int userId);
}