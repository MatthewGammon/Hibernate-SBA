/**
 * 
 */
package com.mgammon.SMS.test;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.StudentService;
import junit.framework.TestCase;

/**
 * @author mgamm
 *
 */
public class StudentServiceTest extends TestCase {
	Student student1, student2;
	StudentService studentService;

	protected void setUp() throws Exception {
		studentService = new StudentService();
		student1 = new Student("hot4u@yahoo.com", "Chloe Alper", "PRRizAwSomE19", new ArrayList<Course>());
		student2 = new Student("imstihere@aloha.com", "M. Geebz", "awwight", new ArrayList<Course>());
	}

	@Test
	public void testValidateStudent() {
		assertEquals(true, studentService.validateStudent(student1.getsEmail(), student1.getsPass()));
		assertEquals(false, studentService.validateStudent(student2.getsEmail(), student2.getsPass()));
	}
}
