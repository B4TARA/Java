package by.baturel.spring.repositories;

import by.baturel.spring.models.FeedbacksEntity;
import by.baturel.spring.models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UsersEntityRepository extends CrudRepository<UsersEntity, Long> {

    //List<UsersEntity> findByName(String name);

    @Query(value = "SELECT * FROM Users where UserLogin = :UserLogin ",nativeQuery = true)
    Optional<UsersEntity> findByName(@Param("UserLogin") String userLogin);

    @Query(value = "SELECT * FROM Users", nativeQuery = true)
    List<UsersEntity> findAll();

    @Query(value = "EXECUTE DUsersSelectId :Id", nativeQuery = true)
    Optional<UsersEntity> findById(@Param("Id") Integer id);

    @Modifying
    @Query(value = "EXECUTE DUserDel :Id", nativeQuery = true)
    void deleteById(@Param("Id") Integer id);

    @Modifying
    @Query(value = "EXECUTE DUserUpdate :Id,:Is_Admin,:User_Login,:User_Password,:EMail", nativeQuery = true)
    void update(@Param("Id") Integer id,
                @Param("Is_Admin") Boolean isAdmin,
                @Param("User_Login") String userLogin,
                @Param("User_Password") String userPassword,
                @Param("EMail") String email

    );

    @Modifying
    @Query(value = "Insert into Users values (:Is_Admin,:User_Login,:User_Password,:EMail)", nativeQuery = true)
    void add(
            @Param("Is_Admin") Boolean isAdmin,
            @Param("User_Login") String userLogin,
            @Param("User_Password") String userPassword,
            @Param("EMail") String email
    );

    @Query(value = "EXECUTE DUsersSelectLogin :User_Login", nativeQuery = true)
    Optional<UsersEntity>  getByName(
            @Param("User_Login") String userLogin
    );

    @Query(value = "EXECUTE DUsersSelectS  :LimitMin,:LimitMax,:Search", nativeQuery = true)
    List<UsersEntity> findPaginated(@Param("LimitMin") Integer limitMin,
                              @Param("LimitMax") Integer limitMax,
                              @Param("Search") String search
    );

    @Query(value = "EXECUTE DUsersSelect :LimitMin,:LimitMax", nativeQuery = true)
    List<UsersEntity> findPaginated(@Param("LimitMin") Integer limitMin,
                              @Param("LimitMax") Integer limitMax

    );

    @Query(value = "SELECT Count(*) from Users",nativeQuery = true)
    int countRows();

    @Query(value = "EXECUTE DUsersSelectCountS :Seacrh",nativeQuery = true)
    int countRows(@Param("Seacrh")String seacrh);

}
