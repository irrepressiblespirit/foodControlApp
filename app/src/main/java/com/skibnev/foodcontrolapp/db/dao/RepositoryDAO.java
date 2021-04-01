package com.skibnev.foodcontrolapp.db.dao;

import com.skibnev.foodcontrolapp.util.CallBack;

import java.util.List;

public interface RepositoryDAO<E> {
    List<E> findAll();

    void writeToDB(String id, E entity);

    void getByName(String name, CallBack callBack);
}
