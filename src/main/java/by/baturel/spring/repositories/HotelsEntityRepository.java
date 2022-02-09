package by.baturel.spring.repositories;

import by.baturel.spring.models.FeedbacksEntity;
import by.baturel.spring.models.HotelsEntity;
import by.baturel.spring.models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface HotelsEntityRepository  extends JpaRepository<HotelsEntity, Long> {
    @Query(value = "SELECT * FROM Hotels where Rooms = :i ",nativeQuery = true)
    Optional<HotelsEntity> findByName(@Param("i") int i);



}
