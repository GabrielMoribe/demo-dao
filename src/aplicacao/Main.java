package aplicacao;

import entidades.Department;
import entidades.Seller;

import java.util.Date;

public class Main {
    public static void main(String[] args) {

        Department obj = new Department(1 , "IT");
        Seller seller = new Seller(21,"bob","bob@email.com",new Date(),3000.0,obj);
        System.out.println(seller);
    }
}