package aplicacao;

import DAO.DaoFactory;
import DAO.SellerDao;
import entidades.Department;
import entidades.Seller;

import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("--- Test 1: Find by ID ---");
        Seller sell = sellerDao.findById(7);
        System.out.println(sell);

        //REVER ESTA PARTE
        System.out.println("--- Test 2: Find by Department ---");
        Department department = new Department(1, null);
        List<Seller> seller_list = sellerDao.findByDepartment(department);
        for(Seller i : seller_list){
            System.out.println(i);
        }

//        System.out.println("--- Test 3: Find All ---");
//        List<Seller> seller_list2 = sellerDao.findAll();
//        for(Seller i : seller_list2){
//            System.out.println(i);
//        }

//        System.out.println("--- Test 4: Insert ---");
//        Seller inserted_seller = new Seller(8   ,"Gabriel","gab@email.com",new Date(),10000.0,department);
//        sellerDao.insert(inserted_seller);
//        System.out.println("Inserting: " + inserted_seller);

        System.out.println("--- Test 5: Update ---");
        sell = sellerDao.findById(8);
        sell.setName("Gabriel Moribe");
        sell.setBaseSalary(15000.0);
        sell.setDepartment(new Department(4,null));
        sellerDao.update(sell);
        System.out.println("Updating: " + sell);

        System.out.println("--- Test 6: Delete ---");
        sellerDao.deleteById(15);
        System.out.println("Deleting" );
      }
}