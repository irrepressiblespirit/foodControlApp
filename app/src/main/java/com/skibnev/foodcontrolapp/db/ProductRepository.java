package com.skibnev.foodcontrolapp.db;

import com.skibnev.foodcontrolapp.db.dao.RepositoryDAO;
import com.skibnev.foodcontrolapp.model.Product;
import com.skibnev.foodcontrolapp.util.CallBack;

import java.util.List;
import java.util.Set;

public class ProductRepository extends FirebaseRepository implements RepositoryDAO<Product> {

    @Override
    public List<Product> findAll() {
        return super.findAll();
    }

    @Override
    public void writeToDB(String id, Product response) {
        super.writeToDB(id, response);
    }

    @Override
    public void getByName(String name, CallBack callBack) {
        if (name != null && !name.isEmpty()) {
            super.getByName(name, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        Product product = (Product) object;
                        callBack.onSuccess(product);
                    } else {
                        callBack.onSuccess(null);
                    }
                }

                @Override
                public void onError(Object object) {
                    callBack.onError(object);
                }
            });
        }
    }

    public void searchByNamePrefix(String prefix, CallBack callBack) {
        if (prefix != null && !prefix.isEmpty()) {
            super.searchByNamePrefix(prefix, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        Set<Product> products = (Set<Product>) object;
                        callBack.onSuccess(products);
                    } else {
                        callBack.onSuccess(null);
                    }
                }

                @Override
                public void onError(Object object) {
                    callBack.onError(object);
                }
            });
        }
    }
}
