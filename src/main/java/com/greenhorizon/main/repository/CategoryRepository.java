package com.greenhorizon.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greenhorizon.main.entity.Category;
import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	
	@Query("SELECT c.id FROM Category c WHERE c.name = :paramName")
    Long findIdByName(@Param("paramName") String name);

}
 