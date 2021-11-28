package com.mumu.emos.api.db.dao;

import java.util.HashMap;
import java.util.Set;

public interface UserMapper {
    Set<String> searchUserPermissions(int userId);

    Integer login(HashMap param);

    HashMap searchUserSummary(int userId);

    int updatePassword(HashMap param);
}