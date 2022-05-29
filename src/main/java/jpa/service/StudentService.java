/**
 * 
 */
package jpa.service;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

/**
 * @author mgamm
 *
 */

public class StudentService implements StudentDAO {

	public List<Student> getAllStudents() {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		String hql = "SELECT s FROM Student s";
		Query<Student> sQuery = session.createQuery(hql);
		try {

			List<Student> students = sQuery.getResultList();
			return students;
		} catch (NoResultException e) {
			System.out.println("No students found!");
			return null;
		} finally {
			session.close();
		}
	}

	public Student getStudentByEmail(String sEmail) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		String hql = "SELECT s FROM Student s WHERE sEmail = :sEmail";
		Query<Student> sQuery = session.createQuery(hql);
		sQuery.setParameter("sEmail", sEmail);
		try {
			Student student = sQuery.uniqueResult();
			return student;
		} catch (NoResultException e) {
			System.out.println("Student not found!");
			return null;
		} finally {
			session.close();
		}

	}

	public boolean validateStudent(String email, String password) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		String hql = "FROM Student s WHERE s.sEmail = :email AND s.sPass = :password";
		Query<Student> sQuery = session.createQuery(hql);
		sQuery.setParameter("email", email);
		sQuery.setParameter("password", password);
		try {
			Student student = sQuery.uniqueResult();
			if (student != null) {
				return true;
			} else {
				return false;
			}

		} catch (NoResultException e) {
			System.out.println("No students found!");
			return false;
		} finally {
			session.close();
		}
	}

	public Course getCourseById(int cId) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Query<Course> query = session.createQuery("SELECT c FROM Course c WHERE id = :id");
		query.setParameter("id", cId);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Invalid course ID!");
			return null;
		} finally {
			session.close();
		}

	}

	public void registerStudentToCourse(String sEmail, int cId) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		EntityTransaction t = session.beginTransaction();
		Student student = getStudentByEmail(sEmail);

		if (student.getsCourses().contains(getCourseById(cId)))
			System.out.println("Student is already registered to that course.");
		else {
			try {
				Course course = getCourseById(cId);
				List<Course> courses = student.getsCourses();
				courses.add(course);
				student.setsCourses(courses);
				session.update(student);
				t.commit();
			} catch (NoResultException e) {
				System.out.println("Unrecognized course id.");
			} catch (NonUniqueObjectException f) {
				System.out.println("You are already registered for this course.");
			} finally {
				session.close();
			}
		}
	}

	public List<Course> getStudentCourses(String sEmail) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		String hql = "SELECT sCourses FROM Student s WHERE s.sEmail = :sEmail";
		Query<Course> q = session.createQuery(hql);
		q.setParameter("sEmail", sEmail);

		try {
			List<Course> courses = q.getResultList();
			return courses;
		} catch (NoResultException e) {
			System.out.println("Student course list does not exist.");
			return null;
		} finally {
			session.close();
		}

	}
}
