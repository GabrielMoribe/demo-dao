package DAO;

import entidades.Department;
import entidades.Seller;

import java.util.List;

public interface SellerDao {

    void insert(Seller dept);
    void update(Seller dept);
    void deleteById(Integer id);

    // RETORNA UM OBJ DEPARTMENT
    Seller findById(Integer id);
    List<Seller> findAll();
}
