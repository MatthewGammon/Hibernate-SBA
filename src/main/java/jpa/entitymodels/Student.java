/**
 * 
 */
package jpa.entitymodels;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author mgamm
 *
 */
@Entity
@Table(name = "Student")
public class Student {
	@Id
	@Column(nullable = false, name = "email", length = 50)
	private String sEmail;

	@Column(nullable = false, name = "name", length = 50)
	private String sName;

	@Column(nullable = false, name = "password", length = 50)
	private String sPass;

	@ManyToMany(targetEntity = Course.class, cascade = { CascadeType.ALL })
	@JoinTable(name = "Student_Course")
	private List<Course> sCourses;

	public Student() {
		sEmail = "";
		sName = "";
		sPass = "";
		sCourses = new ArrayList<Course>();
	}

	public Student(String sEmail, String sName, String sPass, List<Course> sCourses) {
		this();
		this.sEmail = sEmail;
		this.sName = sName;
		this.sPass = sPass;
		this.sCourses = sCourses;
	}

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsPass() {
		return sPass;
	}

	public void setsPass(String sPass) {
		this.sPass = sPass;
	}

	public List<Course> getsCourses() {
		return sCourses;
	}

	public void setsCourses(List<Course> sCourses) {
		this.sCourses = sCourses;
	}
}
