package com.philipp.vivent.data;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	@Query("select q from Question q " + 
            "where q.category.id = :categoryId")
	List<Question> findByCategoryId(@Param("categoryId") Long categoryId);
	
	@Query("select q from Question q " + 
            "where lower(q.questionText) like lower(concat('%', :searchTerm, '%'))")
	List<Question> search(@Param("searchTerm") String searchTerm);
}
