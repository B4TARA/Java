package by.baturel.spring.services;

import java.util.List;
import java.util.Optional;

public interface GeneralService<T> {

    public void delete(int id);
    public Optional<T> getById(int id);
    public void editItem(T item);
    public void add(T item);
    public int getCountRows();
    public int getCountRows(String search);
    public List<T> getAll();
    public List<T> getPaginated(int min,int max);
    public List<T> getPaginated(int min,int max,String seacrh);


}
