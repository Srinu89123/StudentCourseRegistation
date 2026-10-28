package com.example.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.model.Student;
import com.example.service.StudentService;

@RestController
@RequestMapping("/students")
@Validated
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //  Register student
    
    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent( @Valid @RequestBody Student student) 
    {

        Student saved = studentService.registerStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // View all students
    
    @GetMapping
    public List<Student> getAllStudents() 
    {
        return studentService.getAllStudents();
    }

    //  Find by ID
    
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id)
    
    {

        Student student = studentService.getStudentById(id);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    //  Search using RequestParam
    
    @GetMapping("/search")
    public List<Student> searchStudents( @RequestParam(required = false) String name,
                                         @RequestParam(required = false) String course,
                                         @RequestParam(required = false) String department)
    {

        return studentService.searchStudents(name, course, department);
    }

    //  Update all student details
    
    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent
    (@RequestParam Long studentId,@Valid @RequestBody Student student) 
    {

        Student updated = studentService.updateStudent(studentId, student);

        if (updated == null)
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    // Update registration status using RequestParam
    
    @PatchMapping("/status")
    public ResponseEntity<Student> updateStatus(
            @RequestParam Long studentId,
            @RequestParam
            @Pattern(regexp = "REGISTERED|PENDING|CANCELLED",
                     message = "Invalid registration status")
            String status) {

        Student updated =
                studentService.updateStatus(studentId, status);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    // Delete registration using RequestParam
    
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteStudent(@RequestParam Long studentId) {

        boolean deleted = studentService.deleteStudent(studentId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}