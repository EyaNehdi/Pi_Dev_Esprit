package services;


import java.sql.SQLException;
import java.util.List;

public interface IService <T>{
    public void add(T t) throws SQLException;
    void delete(T t);
    void update(T t);
    List<T> readAll() throws SQLException;
    T readById(int id);

}








