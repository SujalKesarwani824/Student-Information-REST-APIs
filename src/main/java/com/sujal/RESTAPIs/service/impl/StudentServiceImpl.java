package com.sujal.RESTAPIs.service.impl;

import com.sujal.RESTAPIs.dto.AddStudentRequestDto;
import com.sujal.RESTAPIs.dto.StudentDto;
import com.sujal.RESTAPIs.service.StudentService;
import com.sujal.RESTAPIs.entity.Student;
import com.sujal.RESTAPIs.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service  // Kyuki yeh class hai aur hum iska Bean bana chahte hai isliye @Service jiske andar apne aap hi milta hai @Component hai
// Yeh service annotation batata hai ki saara ka saara Business logic ka code yaha pe hoga
@RequiredArgsConstructor // isse hume jitne bhi required constructor chaiye honge mil jaayenge jitne bhi variable ke liye jisse hum final se likhenge
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    //    MODEL MAPPER IMPLEMENTATION
    private final ModelMapper modelMapper;

    @Override
    public List<StudentDto> getAllStudent() {
        List<Student> students = studentRepository.findAll();

//        Stream API = a way to process data (filter, transform, sort, etc.) without modifying the original collection.

// Learn about stream api and stream is modern way and for loop is old way and its operations
//        Ab yaha hum inline varible banake return kar rahe hai

        return students
                .stream()
                .map( student -> new StudentDto(student.getId(), student.getName(), student.getEmail()))
                .toList();
//        toh humaari service yaha ban chuki hai hume isse use karna hai controller ke andar toh hum Controller mai jaaake
//        isko change kar denge (private final StudentRepository studentRepository;) toh isko hume ab Service ka access dena padega
//        private final StudentService studentService; aise aur fir hum @RequiredArgsConstructor yeh use karke required constructor apne aap hi ban jaayega

        //Yaha hum varible banaake return kar rahe the
//        List<StudentDto> studentDtoList = students
//                .stream()
//                .map( student -> new StudentDto(student.getId(), student.getName(), student.getEmail()))
//                .toList();

//        return studentDtoList;
//    }
    }


    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found with ID: "+id)); //orElseThrow() hume optional class hai java ki usse milta hai
//        Ab mujhe Student ko student Dto mai convert karna hoga StudentDto ke andar jaise upar object banake(stream api) return kiya toh baar baar code likhna comburssiom ho jaayega
//        toh hum chahte yeh code apne aap hi koi library likh de Hum Modelmapper library ka istemaal karenge joh model ko map karne ke kaam aata hai
//        toh hum iski dependency ko daal denge pom.xml mai . Iss Model Mapper ko use karna hai toh mujhe uska Bean banana padega in config package (MapperConfig) because multiple type ki configuration ho sakti hai
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public StudentDto createNewStudent(AddStudentRequestDto addStudentRequestDto) {
        Student newStudent = modelMapper.map(addStudentRequestDto, Student.class); // yeh sirf Spring ki memory mai hai aur agar mujhe Database mai daalna hai toh studentRepository ka save method call karna padega
        Student student = studentRepository.save(newStudent);
        return modelMapper.map(student, StudentDto.class); // yaha ab hume joh student milega uske andar id bhi hogi
    }

    @Override
    public void deleteStudentById(Long id) {
        if(!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student not found with id" + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    public StudentDto updateStudent(Long id, AddStudentRequestDto addStudentRequestDto) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found with ID: "+id));

        modelMapper.map(addStudentRequestDto, student);
        student = studentRepository.save(student);

        return modelMapper.map(student, StudentDto.class);

    }

    @Override
    public StudentDto updatePartialStudent(Long id, Map<String, Object> updates) {
            Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found with ID: "+id));

            updates.forEach((field,value) -> {
                switch (field) {
                    case "name": student.setName((String) value);
                    break;
                    case "email": student.setEmail((String) value);
                    break;
                    default:throw new IllegalArgumentException("Field Not Supported");
                }
            });
            Student savedStudent = studentRepository.save(student);
            return modelMapper.map(savedStudent, StudentDto.class);

    }
//    2️⃣ This line (MOST IMPORTANT)
//if (!studentRepository.existsById(id)) {

//🔹 What is existsById(id)?
//
//                It checks whether a record with this ID exists in the database
//
//        Returns:
//
//        true → student exists
//
//        false → student does NOT exist
//
//        Example:
//
//        existsById(5L) → true   // student with id 5 exists
//        existsById(99L) → false // student with id 99 does not exist
//
//        3️⃣ What does the ! sign mean?
//                ! = NOT operator
//
//        It simply reverses the boolean value.
//
//        Expression	Result
//        true	false
//        false	true
//
//        So:
//
//        !studentRepository.existsById(id)
//
//
//        means:
//
//❌ “Student does NOT exist with this ID”
//
//        Example:
//        existsById(id) → false
//        !existsById(id) → true
//
//
//        That’s why the if block runs only when the student is NOT found.
//
//        4️⃣ Throwing exception
//        throw new IllegalArgumentException("Student not found with id " + id);
//
//
//        Stops execution immediately
//
//        Tells the caller (usually controller):
//
//“You are trying to delete a student that doesn’t exist”
//
//        Without this check:
//
//        deleteById(id) might silently fail
//
//        Or throw a confusing DB exception
//
//👉 This is good practice


//    6️⃣ Where do existsById and deleteById come from?
//
//    You did NOT write them, but they still work 🤯
//    Why?
//
//    Because your repository looks like this:
//    public interface StudentRepository extends JpaRepository<Student, Long> {
//    }
//
//    JpaRepository already provides these methods






}



//🔹 Why students and student are different?
//        1️⃣ students (plural)
//List<Student> students = studentRepository.findAll();
//
//
//students = a list (collection)
//
//It contains many Student objects
//
//Think like:
//
//students = [Student1, Student2, Student3]
//
//        2️⃣ students.stream()
//students.stream()
//
//
//Converts the list into a stream
//
//Stream will take one student at a time
//
//3️⃣ student (singular) inside map
//.map(student -> new StudentDto(...))
//
//
//student is ONE element from the list
//
//This name is chosen by YOU (can be anything)
//
//Think like a loop 👇
//
//        for (Student student : students) {
//        // student = one Student at a time
//        }
//
//
//        💡 Stream map() is doing the same thing internally.
//
//        🔁 Compare with for loop (VERY IMPORTANT)
//for loop
//for (Student student : students) {
//        System.out.println(student.getName());
//        }
//
//
//students → whole list
//
//student → one object from the list
//
//        stream
//students.stream()
//        .map(student -> student.getName())
//        .toList();
//
//
//students → whole list
//
//student → one object from the list
//
//👉 SAME LOGIC, different syntax.
//
//🧠 Simple analogy (best way to remember)
//🧺 Basket example
//
//students = basket of apples 🍎🍎🍎
//
//student = one apple 🍎
//
//Stream takes one apple at a time.
//
//❓ Can I name it something else?
//
//YES 👍
//These are all valid:
//
//        .map(s -> new StudentDto(...))
//
//        .map(x -> new StudentDto(...))
//
//        .map(stu -> new StudentDto(...))
//
//
//But best practice is:
//
//        .map(student -> ...)
//
//
//So code is readable.
