package com.crud.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.crud.model.Student;
import com.crud.model.StudentDTO;

@Mapper(componentModel = "spring")
public interface StudentMapper {

	StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

	@Mapping(source = "email", target = "email")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "department", target = "department")
	StudentDTO convertToDTO(Student student);

	List<StudentDTO> convertToDTOList(List<Student> students);
}
