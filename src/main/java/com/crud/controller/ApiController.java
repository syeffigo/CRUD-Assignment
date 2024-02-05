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

import com.crud.mapper.StudentMapper;
import com.crud.model.Student;
import com.crud.model.StudentDTO;
import com.crud.repository.StudentRepository;

@RestController
public class ApiController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private StudentMapper studentMapper;

	@GetMapping("/read")
	private ResponseEntity<List<StudentDTO>> getAllStudentsRecord() {
		List<Student> students = studentRepository.findAll();
		List<StudentDTO> data = studentMapper.convertToDTOList(students);

		return ResponseEntity.ok(data);
	}

	@GetMapping("/read/{id}")
	private ResponseEntity<?> getStudentRecordById(@PathVariable Integer id) {
		try {
			Student student = studentRepository.getById(id);
			StudentDTO data = studentMapper.convertToDTO(student);

			return ResponseEntity.ok(data);
		} catch (Exception e) {
			String errorMessage = "Student with ID " + id + " not found. Please update the id.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
	}

	@PostMapping("/create")
	private ResponseEntity<String> createStudentRecord(@RequestBody Student student) {

		boolean checkEmailExist = studentRepository.existsByEmail(student.getEmail());

		if (checkEmailExist) {
			return ResponseEntity.ok("Can't Add this Student. As Email Id already exists");
		}
		studentRepository.save(student);

		return ResponseEntity.ok("Student Record Added Successfully");
	}

	@PutMapping("/update/{id}")
	private ResponseEntity<?> updateStudentRecord(@PathVariable Integer id, @RequestBody Student student) {
		try {
			Student data = studentRepository.getById(id);

			if (student.getEmail() != null) {
				data.setEmail(student.getEmail());
			}

			if (student.getName() != null) {
				data.setName(student.getName());
			}

			if (student.getDepartment() != null) {
				data.setDepartment(student.getDepartment());
			}

			studentRepository.save(data);

			StudentDTO dto = studentMapper.convertToDTO(data);

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
