package com.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.crud.dto.StudentDTO;
import com.crud.mapper.StudentMapper;
import com.crud.model.Student;
import com.crud.repository.StudentRepository;

@Component
public class StudentService {
  @Autowired
  private StudentRepository studentRepository;
  
  @Autowired
  private StudentMapper studentMapper;
  
  public List<StudentDTO> getAllStudents(){
	  List<Student> students = studentRepository.findAll();
	  List<StudentDTO> data = studentMapper.convertToDTOList(students);
	  
	  return data;
  }
  
  public StudentDTO getStudent(Integer id) {
		  Optional<Student> student = studentRepository.findById(id);
		  Student cur_student = student.get();
		  StudentDTO data = studentMapper.convertToDTO(cur_student);
		  return data;
  }
  
  public String createStudentRecord(StudentDTO studentDTO) {
	  
	  boolean checkEmailExist = studentRepository.existsByEmail(studentDTO.getEmail());
	  if(checkEmailExist) {
		  return "User already exists";
	  }
	  else {
		  Student student = studentMapper.studentDTOToStudent(studentDTO);
		  studentRepository.save(student);
		  
		  return "User Registered Successfully";
	  }
  }
  
  public String getPassword(String email) {
	  boolean checkEmailExist = studentRepository.existsByEmail(email);
	  
	  if(checkEmailExist) {
		  String password = studentRepository.findPasswordByEmail(email);
		  return password;
	  }
	  else {
		  return "User doen't exists";
	  }
  }
  
  public StudentDTO updateStudentRecord(Integer id, StudentDTO studentDTO) {
	  Optional<Student> data = studentRepository.findById(id);
	  Student student = data.get();

		if (studentDTO.getEmail() != null) {
			student.setEmail(studentDTO.getEmail());
		}

		if (studentDTO.getName() != null) {
			student.setName(studentDTO.getName());
		}

		if (studentDTO.getDepartment() != null) {
			student.setDepartment(studentDTO.getDepartment());
		}

		studentRepository.save(student);
        StudentDTO cur_dto = studentMapper.convertToDTO(student);
        
        return cur_dto;
  }
  
  
}
