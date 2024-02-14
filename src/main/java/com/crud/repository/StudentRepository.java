package com.crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crud.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	public boolean existsByEmail(String email);
	
	Optional<Student> findById(Integer id);
	

    @Query(value = "SELECT password FROM Student WHERE email = :email", nativeQuery = true)
    String findPasswordByEmail(@Param("email") String email);
}

