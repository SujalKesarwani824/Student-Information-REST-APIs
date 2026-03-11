package com.sujal.RESTAPIs.repository;

import com.sujal.RESTAPIs.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

}
