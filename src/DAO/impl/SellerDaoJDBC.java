package DAO.impl;

import DAO.SellerDao;
import db.DB;
import db.DbException;
import entidades.Department;
import entidades.Seller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn ;
    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller dept) {

    }

    @Override
    public void update(Seller dept) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.id "
                    + "WHERE seller.id = ? ");
            st.setInt(1,id);
            rs = st.executeQuery();
            if(rs.next()){
                Department dept = instanciateDepartment(rs);
                Seller sell = instanciateSeller(rs,dept);
                return sell;
            }
            return null;
        }
        catch(SQLException e){
            throw new DbException("Erro ao buscar o seller com id = " + id + "\nERRO: " + e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    public Department instanciateDepartment(ResultSet rs) throws SQLException {
        Department dept = new Department();
        dept.setId(rs.getInt("DepartmentId"));
        dept.setName(rs.getString("DepName"));
        return dept;
    }
    public Seller instanciateSeller(ResultSet rs, Department dept) throws SQLException {
        Seller sell = new Seller();
        sell.setId(rs.getInt("Id"));
        sell.setName(rs.getString("Name"));
        sell.getEmail(rs.getString("Email"));
        sell.getBaseSalary(rs.getDouble("BaseSalary"));
        sell.setBirthdate(rs.getDate("BirthDate"));
        sell.setDepartment(dept);
        return sell;
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
