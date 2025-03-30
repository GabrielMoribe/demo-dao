package aplicacao;

import DAO.DaoFactory;
import DAO.SellerDao;
import entidades.Department;
import entidades.Seller;

import java.util.List;


public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("--- Test 1: Find by ID ---");
        Seller sell = sellerDao.findById(7);
        System.out.println(sell);

        
        System.out.println("--- Test 2: Find by Department ---");
        Department department = new Department(1, null);
        List<Seller> seller_list = sellerDao.findByDepartment(department);
        for(Seller i : seller_list){
            System.out.println(i);
        }
    }
}