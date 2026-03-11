package com.sujal.RESTAPIs.controller;

import com.sujal.RESTAPIs.dto.AddStudentRequestDto;
import com.sujal.RESTAPIs.dto.StudentDto;
import com.sujal.RESTAPIs.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RequestMapping("/students")
@RequiredArgsConstructor
@RestController
public class StudentController {


    private final StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudent());

    }


    @GetMapping("students/{id}")
    public ResponseEntity<StudentDto> getStudentByID(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentById(id));

    }

    @PostMapping
    public ResponseEntity<StudentDto> createNewStudent(@RequestBody @Valid AddStudentRequestDto addStudentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createNewStudent(addStudentRequestDto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAstudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id,
                                                    @RequestBody AddStudentRequestDto addStudentRequestDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, addStudentRequestDto));

    }


    @PatchMapping("/{id}")
        public ResponseEntity<StudentDto> updatePartialStudent (@PathVariable Long id, @RequestBody Map < String, Object > updates ){
            return ResponseEntity.ok(studentService.updatePartialStudent(id, updates));

        }


}
