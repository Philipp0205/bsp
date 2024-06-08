package com.philipp.vivent.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	@Query("select c from Category c " + 
			"where lower(c.name) like lower(concat('%', :searchTerm, '%'))")
	List<Category> search(@Param("searchTerm") String searchTerm); }
