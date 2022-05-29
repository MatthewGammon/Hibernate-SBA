package jpa.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;

/**
 * @author mgamm
 *
 */
public class CourseService implements CourseDAO {
	public List<Course> getAllCourses() {

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		String hql = "SELECT c FROM Course c";
		Query<Course> sQuery = session.createQuery(hql);

		try {
			List<Course> courses = sQuery.getResultList();
			return courses;
		} catch (NoResultException e) {
			System.out.println("No courses found!");
			return null;
		} finally {
			session.close();
		}
	}

}
