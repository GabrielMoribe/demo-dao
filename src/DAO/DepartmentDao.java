package DAO;

import entidades.Department;

import java.util.List;

public interface DepartmentDao {

    void insert(Department dept);
    void update(Department dept);
    void deleteById(Integer id);

    // RETORNA UM OBJ DEPARTMENT
    Department findById(Integer id);
    List<Department> findAll();
}
