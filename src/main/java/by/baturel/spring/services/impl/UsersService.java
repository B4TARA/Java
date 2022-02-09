package by.baturel.spring.services.impl;

import by.baturel.spring.models.UsersEntity;
import by.baturel.spring.repositories.UsersEntityRepository;
import by.baturel.spring.services.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService implements GeneralService<UsersEntity> {

    private UsersEntityRepository usersRepository;

    @Autowired
    public UsersService(UsersEntityRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void delete(int id) {
        usersRepository.deleteById((long) id);
    }

    @Override
    public Optional<UsersEntity> getById(int id) {
        return usersRepository.findById((long) id);
    }

    @Override
    public void editItem(UsersEntity item) {
        usersRepository.update(Math.toIntExact(item.getId()),item.isAdmin(),item.getUserLogin(),item.getUserPassword(),item.getUserLogin());
    }

    @Override
    public void add(UsersEntity item) {
        usersRepository.add(item.isAdmin(),item.getUserLogin(),item.getUserPassword(),item.geteMail());
    }

    @Override
    public int getCountRows() {
        return usersRepository.countRows();
    }

    @Override
    public int getCountRows(String search) {
        return usersRepository.countRows(search);
    }

    @Override
    public List<UsersEntity> getAll() {
        return usersRepository.findAll();
    }

    @Override
    public List<UsersEntity> getPaginated(int min, int max) {
        return usersRepository.findPaginated(min,max);
    }

    @Override
    public List<UsersEntity> getPaginated(int min, int max, String seacrh) {
        return usersRepository.findPaginated(min,max,seacrh);
    }

    public Optional<UsersEntity> findByName(String name) {
        return usersRepository.findByName(name);
    }


}
