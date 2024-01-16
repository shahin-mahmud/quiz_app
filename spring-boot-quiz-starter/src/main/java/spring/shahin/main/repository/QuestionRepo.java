package spring.shahin.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.shahin.main.model.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

}