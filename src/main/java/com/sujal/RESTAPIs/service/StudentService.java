package com.sujal.RESTAPIs.service;

import com.sujal.RESTAPIs.dto.AddStudentRequestDto;
import com.sujal.RESTAPIs.dto.StudentDto;


import java.util.List;
import java.util.Map;

public interface StudentService {

   List<StudentDto>  getAllStudent(); // iss method ko implement karna hai toh service ke andar ek package banayenge Impl

    StudentDto getStudentById(Long id);


    StudentDto createNewStudent(AddStudentRequestDto addStudentRequestDto);

    void deleteStudentById(Long id);

     StudentDto updateStudent(Long id, AddStudentRequestDto addStudentRequestDto);

     StudentDto updatePartialStudent(Long id, Map<String, Object> updates);
}
