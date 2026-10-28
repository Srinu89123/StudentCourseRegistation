package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Student;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();
    private long nextId = 1;

    // Register student
    
    public Student registerStudent(Student student)
    {
        student.setStudentId(nextId++);
        students.add(student);
        return student;
    }

    // View all students
    
    public List<Student> getAllStudents() 
    {
        return new ArrayList<>(students);
    }

    // Find student by ID
    
    public Student getStudentById(Long id)
    {
        return students.stream()
                .filter(s -> s.getStudentId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Search students using optional filters
    
    public List<Student> searchStudents(
            String name, String course, String department)
    {

        return students.stream()
                .filter(s -> name == null
                        || s.getStudentName().toLowerCase()
                        .contains(name.toLowerCase()))
                .filter(s -> course == null
                        || s.getCourseName().equalsIgnoreCase(course))
                .filter(s -> department == null
                        || s.getDepartment().equalsIgnoreCase(department))
                .toList();
    }

    // Update all student details
    
    public Student updateStudent(Long id, Student updatedStudent)
    {
        Student existing = getStudentById(id);

        if (existing == null) {
            return null;
        }

        updatedStudent.setStudentId(id);
        students.set(students.indexOf(existing), updatedStudent);
        return updatedStudent;
    }

    // Update registration status
    
    public Student updateStatus(Long id, String status)
    {
        Student student = getStudentById(id);

        if (student != null) 
        {
            student.setRegistrationStatus(status);
        }

        return student;
    }

    // Delete registration
    
    public boolean deleteStudent(Long id) 
    {
        return students.removeIf(
                s -> s.getStudentId().equals(id));
    }
}