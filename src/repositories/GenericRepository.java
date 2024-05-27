package repositories;

import java.sql.SQLException;

public interface GenericRepository<T> {
    public void add(T entity) throws SQLException;
    public T get(int id) throws SQLException;
    public void update(T entity) throws SQLException;
    public void delete(T entity) throws SQLException;
    public int getSize() throws SQLException;
}