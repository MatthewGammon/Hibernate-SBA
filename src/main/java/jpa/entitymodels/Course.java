/**
 * 
 */
package jpa.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author mgamm
 *
 */
@Entity
@Table(name = "Course")
public class Course {
	@Id
	@Column(nullable = false, name = "id")
	private int cId;

	@Column(nullable = false, name = "name", length = 50)
	private String cName;

	@Column(nullable = false, name = "instructor", length = 50)
	private String cInstructorName;

	public Course() {
		cId = 0;
		cName = "";
		cInstructorName = "";
	}

	public Course(int cId, String cName, String cInstructorName) {
		this.cId = cId;
		this.cName = cName;
		this.cInstructorName = cInstructorName;
	}

	public int getId() {
		return cId;
	}

	public void setId(int cId) {
		this.cId = cId;
	}

	public String getName() {
		return cName;
	}

	public void setName(String cName) {
		this.cName = cName;
	}

	public String getInstructor() {
		return cInstructorName;
	}

	public void setInstructor(String cInstructorName) {
		this.cInstructorName = cInstructorName;
	}

}
