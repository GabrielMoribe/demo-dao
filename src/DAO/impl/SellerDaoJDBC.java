package DAO.impl;

import DAO.SellerDao;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import db.DB;
import db.DbException;
import entidades.Department;
import entidades.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn ;
    public SellerDaoJDBC(Connection conn) {
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
    public void insert(Seller dept) {
        PreparedStatement st = null;
        try{
            st=conn.prepareStatement("INSERT INTO seller "
                                       + "(Name , Email , BirthDate , BaseSalary , DepartmentId) "
                                       + "VALUES "
                                       + "(? , ? , ? , ? , ?)",
                                       + Statement.RETURN_GENERATED_KEYS);
            st.setString(1,dept.getName());
            st.setString(2,dept.getEmail());
            st.setDate(3,new java.sql.Date(dept.getBirthdate().getTime()));
            st.setDouble(4,dept.getBaseSalary());
            st.setInt(5,dept.getDepartment().getId());

            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    dept.setId(rs.getInt(1));
                }
                DB.closeResultSet(rs);
            }
            else{
                throw new DbException("Erro ao inserir o seller ");
            }
        }
        catch(SQLException e){
            throw new DbException("Erro ao inserir o seller \nERRO: " + e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller dept) {

        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("UPDATE seller "
                    + "SET Name = ? , Email = ? , BirthDate = ? , BaseSalary = ? , DepartmentId = ? "
                    + "WHERE id = ? "
            );
            st.setString(1,dept.getName());
            st.setString(2,dept.getEmail());
            st.setDate(3,new java.sql.Date(dept.getBirthdate().getTime()));
            st.setDouble(4,dept.getBaseSalary());
            st.setInt(5,dept.getDepartment().getId());
            st.setInt(6,dept.getId());
            st.executeUpdate();

        }
        catch(SQLException e){
            System.out.println("Erro ao atualizar o seller\nErro: " + e.getMessage());
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
            st = conn.prepareStatement("DELETE FROM seller WHERE id = ?");
            st.setInt(1,id);
            int rowsAffected = st.executeUpdate();
            if(rowsAffected==0){
                conn.rollback();
                throw new DbException("Transacao recusada, Não foi possivel deletar o seller");
            }
            else{
                System.out.println("O seller foi deletado com sucesso");
                conn.commit();
            }
        }
        catch(SQLException e){
            System.out.println("Erro ao deletar o seller\nErro: " + e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
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

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.id "
                            + "ORDER BY seller.Name ");
            rs = st.executeQuery();
            List<Seller> sellers_list = new ArrayList<>();
            // MAPS ASSOCIAM CHAVES-VALORES
            // CHAVE: UM IDENTIFICADOR
            // VALOR: TIPO DO OBJETO RETORNADO
            Map<Integer , Department> map = new HashMap<>();

            while(rs.next()){
                Department dept = map.get(rs.getInt("DepartmentId"));
                if(dept == null){
                    dept=instanciateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dept);
                }
                Seller sell = instanciateSeller(rs,dept);
                sellers_list.add(sell);
            }
            return sellers_list;
        }
        catch(SQLException e){
            throw new DbException("Erro ao buscar o seller \nERRO: " + e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                        + "FROM seller INNER JOIN department "
                        + "ON seller.DepartmentId = department.id "
                        + "WHERE department.id = ? "
                        + "ORDER BY seller.Name ");

            st.setInt(1,department.getId());
            rs = st.executeQuery();
            List<Seller> sellers_list = new ArrayList<>();
            // MAPS ASSOCIAM CHAVES-VALORES
            // CHAVE: UM IDENTIFICADOR
            // VALOR: TIPO DO OBJETO RETORNADO
            Map<Integer , Department> map = new HashMap<>();

            while(rs.next()){
                Department dept = map.get(rs.getInt("DepartmentId"));
                if(dept == null){
                    dept=instanciateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dept);
                }
                Seller sell = instanciateSeller(rs,dept);
                sellers_list.add(sell);
            }
            return sellers_list;
        }
        catch(SQLException e){
            throw new DbException("Erro ao buscar o seller \nERRO: " + e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
