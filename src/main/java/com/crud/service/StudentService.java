package com.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.dto.StudentDTO;
import com.crud.mapper.StudentMapper;
import com.crud.model.Student;
import com.crud.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private StudentMapper studentMapper;

	public List<StudentDTO> getAllStudents() {
		log.info("Fetching all students from the database");
		List<Student> students = studentRepository.findAll();
		List<StudentDTO> data = studentMapper.convertToDTOList(students);
		log.debug("Fetched {} students", data.size());
		return data;
	}

	public StudentDTO getStudent(Integer id) {
		log.info("Fetching student with ID {}", id);
		Optional<Student> student = studentRepository.findById(id);
		Student cur_student = student.get();
		StudentDTO data = studentMapper.convertToDTO(cur_student);
		log.debug("Fetched student details: {}", data);
		return data;
	}

	public String createStudentRecord(StudentDTO studentDTO) {
		log.info("Creating student record");
		boolean checkEmailExist = studentRepository.existsByEmail(studentDTO.getEmail());
		if (checkEmailExist) {
			log.warn("User with email {} already exists", studentDTO.getEmail());
			return "User already exists";
		} else {
			Student student = studentMapper.studentDTOToStudent(studentDTO);
			studentRepository.save(student);
			log.info("User registered successfully");
			return "User Registered Successfully";
		}
	}

	public String getPassword(String email) {
		log.info("Fetching password for email {}", email);
		boolean checkEmailExist = studentRepository.existsByEmail(email);

		if (checkEmailExist) {
			String password = studentRepository.findPasswordByEmail(email);
			log.debug("Password fetched successfully");
			return password;
		} else {
			log.warn("User with email {} doesn't exist", email);
			return "User doen't exists";
		}
	}

	public StudentDTO updateStudentRecord(Integer id, StudentDTO studentDTO) {
		log.info("Updating student record with ID {}", id);
		Optional<Student> data = studentRepository.findById(id);
		Student student = data.get();

		if (studentDTO.getEmail() != null) {
			student.setEmail(studentDTO.getEmail());
			log.info("Updated email for student with ID {} to {}", id, studentDTO.getEmail());
		}

		if (studentDTO.getName() != null) {
			student.setName(studentDTO.getName());
			log.info("Updated name for student with ID {} to {}", id, studentDTO.getName());
		}

		if (studentDTO.getDepartment() != null) {
			student.setDepartment(studentDTO.getDepartment());
			log.info("Updated department for student with ID {} to {}", id, studentDTO.getDepartment());
		}

		studentRepository.save(student);
		log.debug("Student record updated successfully");
		StudentDTO cur_dto = studentMapper.convertToDTO(student);
		return cur_dto;
	}
}
