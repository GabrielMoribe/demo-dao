package DAO.impl;

import DAO.DaoFactory;
import DAO.DepartmentDao;

import db.DB;
import db.DbException;
import entidades.Department;
import entidades.Seller;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn ;
    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
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
        sell.setEmail(rs.getString("Email"));
        sell.setBaseSalary(rs.getDouble("BaseSalary"));
        sell.setBirthdate(rs.getDate("BirthDate"));
        sell.setDepartment(dept);
        return sell;
    }

    @Override
    public void insert(Department dept) {
        PreparedStatement st = null;
        try{
            st=conn.prepareStatement("INSERT INTO department " +
                    "(Name) " +
                    "VALUES " +
                    "(?) ",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1,dept.getName());
            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    dept.setId(rs.getInt(1));
                }
                DB.closeResultSet(rs);
            }
            else{
                throw new DbException("Erro ao inserir o department ");
            }
        }
        catch(SQLException e){
            throw new DbException("Erro ao inserir o department \nERRO: " + e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Department dept) {
        PreparedStatement st = null;
        try{
            st=conn.prepareStatement("UPDATE department " +
                    "SET Name=? " +
                    "WHERE id=? ");
            st.setString(1,dept.getName());
            st.setInt(2,dept.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o department\nErro: " + e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            conn.setAutoCommit(false);
            st=conn.prepareStatement("DELETE FROM department WHERE id=?");
            st.setInt(1,id);
            int rowsAffected = st.executeUpdate();
            if(rowsAffected==0){
                conn.rollback();
                throw new SQLException("Transação recusada!");
            }
            else{
                System.out.println("O department foi deletado com sucesso");
                conn.commit();
            }
        }
        catch (SQLException e){
            System.out.println("Erro ao deletar o department\nErro: " + e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT * FROM department WHERE Id=?");
            st.setInt(1,id);
            rs=st.executeQuery();
            if(rs.next()){
                //Department dept = (Department) DaoFactory.createDepartmentDao();
                Department dept = new Department();
                dept.setName(rs.getString("Name"));
                dept.setId(rs.getInt("Id"));
                return dept;
            }
            return null;
        }
        catch(SQLException e){
            throw new DbException("Erro ao buscar o department com id = " + id + "\nERRO: " + e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st=conn.prepareStatement("SELECT * FROM department ORDER BY Name");
            rs=st.executeQuery();
            List<Department> dept_list = new ArrayList<>();

            while(rs.next()){
                Department dept = new Department();
                dept.setId(rs.getInt("Id"));
                dept.setName(rs.getString("Name"));
                dept_list.add(dept);
            }
            return dept_list;
        }
        catch(SQLException e){
            throw new DbException("Erro ao buscar o department \nERRO: " + e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
