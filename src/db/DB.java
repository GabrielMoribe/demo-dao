package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection(){
        if(conn==null){
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            }
            catch(SQLException e){
                throw new DbException("Erro ao conectar ao banco de dados\nERRO: " + e.getMessage() + "\n");
            }
        }
        return conn;
    }


    private static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;
        }
        catch (IOException e) {
            throw new DbException("Erro ao carregar arquivo de propriedades no caminho: " + new File("db.properties").getAbsolutePath() + "\nERRO: " + e.getMessage());
        }


    }
    public static void closeStatement(Statement st){
        if(st != null){
            try{
                st.close();
            }
            catch (SQLException e){
                throw new DbException("Erro ao fechar o statement\nERRO: " + e.getMessage() + "\n");
            }
        }
    }
    public static void closeResultSet(ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            }
            catch (SQLException e){
                throw new DbException("Erro ao fechar o ResultSet\nERRO: " + e.getMessage() + "\n");
            }
        }
    }
    public static void closeConnection(){
        if(conn != null){
            try {
                conn.close();
            }
            catch(SQLException e){
                throw new DbException("Erro ao acessar o banco de dados\nERRO: " + e.getMessage() + "\n");
            }
        }
    }
}
