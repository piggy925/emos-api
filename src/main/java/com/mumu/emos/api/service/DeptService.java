package com.mumu.emos.api.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface DeptService {
    ArrayList<HashMap> searchAllDept();

    HashMap searchById(int id);
}