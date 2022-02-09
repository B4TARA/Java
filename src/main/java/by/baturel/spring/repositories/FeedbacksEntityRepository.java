package by.baturel.spring.repositories;

import by.baturel.spring.models.FeedbacksEntity;
import by.baturel.spring.models.HotelsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface FeedbacksEntityRepository extends JpaRepository<FeedbacksEntity, Long> {
}
