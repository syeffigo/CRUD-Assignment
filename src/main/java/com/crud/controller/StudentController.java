package com.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.dto.StudentDTO;
import com.crud.mapper.StudentMapper;
import com.crud.model.Student;
import com.crud.repository.StudentRepository;
import com.crud.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentService studentService;

	@GetMapping("/read")
	private ResponseEntity<List<StudentDTO>> getAllStudentsRecord() {
        List<StudentDTO> data = studentService.getAllStudents();
        return ResponseEntity.ok(data);
	}
	
	@GetMapping("/native")
	private String getPassword(@RequestBody String email) {
		return studentService.getPassword(email);
	}

	@GetMapping("/read/{id}")
	private ResponseEntity<?> getStudentRecordById(@PathVariable Integer id) {
		try {
	         StudentDTO data = studentService.getStudent(id);
			return ResponseEntity.ok(data);
		} catch (Exception e) {
			String errorMessage = "Student with ID " + id + " not found. Please update the id.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
	}

	@PostMapping("/create")
	private ResponseEntity<String> createStudentRecord(@RequestBody StudentDTO studentDTO) {
           String result = studentService.createStudentRecord(studentDTO);
           return ResponseEntity.ok(result);
	}

	@PutMapping("/update/{id}")
	private ResponseEntity<?> updateStudentRecord(@PathVariable Integer id, @RequestBody StudentDTO studentDTO) {
		try {
            StudentDTO dto = studentService.updateStudentRecord(id, studentDTO);
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			String errorMessage = "Student with ID " + id + " not found. Please update the id.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
	}

	@DeleteMapping("/delete/{id}")
	private ResponseEntity<String> deleteStudentRecord(@PathVariable Integer id) {
		try {
			studentRepository.deleteById(id);
			return ResponseEntity.ok("Student Record Deleted Successfully");
		} catch (Exception e) {
			String errorMessage = "Student with ID " + id + " not found. Please update the id.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
	}

}
