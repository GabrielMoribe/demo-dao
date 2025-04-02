package aplicacao;

import DAO.DaoFactory;
import DAO.DepartmentDao;
import entidades.Department;
import java.util.List;


public class Main2 {
    public static void main(String[] args) {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        System.out.println("--- Test 1: Find by ID ---");
        Department dept = departmentDao.findById(1);
        System.out.println(dept);


        System.out.println("--- Test 2: FindAll ---");
        List<Department> dept_list = departmentDao.findAll();
        for(Department i : dept_list){
            System.out.println(i);
        }

//        System.out.println("--- Test 3: Insert ---");
//        Department inserted_dept = new Department(7,"TI");
//        departmentDao.insert(inserted_dept);
//        System.out.println("Inserting: " + inserted_dept);

        System.out.println("--- Test 5: Update ---");
        dept = departmentDao.findById(8);
        dept.setName("Finance");
        departmentDao.update(dept);
        System.out.println("Updating: " + dept);
//
//        System.out.println("--- Test 6: Delete ---");
//        departmentDao.deleteById(9);
//        System.out.println("Deleting" );
    }
}