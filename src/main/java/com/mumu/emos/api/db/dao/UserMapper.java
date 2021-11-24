package com.mumu.emos.api.db.dao;

import java.util.Set;

public interface UserMapper {
    Set<String> searchUserPermissions(int userId);
}