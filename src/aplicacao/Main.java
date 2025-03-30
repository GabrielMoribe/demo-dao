package aplicacao;

import DAO.DaoFactory;
import DAO.SellerDao;
import entidades.Seller;


public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("--- Test 1: Find by ID ---");
        Seller sell = sellerDao.findById(7);
        System.out.println(sell);
    }
}