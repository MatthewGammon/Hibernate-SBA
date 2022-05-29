/**
 * 
 */
package jpa.mainrunner;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import jpa.entitymodels.Course;
import jpa.service.CourseService;
import jpa.service.StudentService;

/**
 * @author mgamm
 *
 */
public class SMSRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StudentService studentService = new StudentService();

		String email = "";
		String password = "";

		Scanner input = new Scanner(System.in);

		int choice = 0;

		boolean skipInput = false;
		boolean isLoggedIn = false;
		while (choice != 2) {
			int userChoice = 0;
			menu();
			System.out.printf("Please select an option: ");
			try {
				choice = input.nextInt();
				input.nextLine();

				if (choice == 1 || choice == 2) {
					switch (choice) {
					case 1:
						System.out.printf("Enter your email: ");
						email = input.nextLine();
						System.out.printf("Enter your password: ");
						password = input.nextLine();

						if (studentService.validateStudent(email, password)) {
							isLoggedIn = true;
							break;
						} else {
							System.out.println("\n\nInvalid login information.  Please try again.\n");
							continue;
						}
					case 2:
						System.out.println("\nNow exiting program...\n\n");
						break;
					}

					if (isLoggedIn) {
						while (userChoice != 2) {
							try {
								if (skipInput == false) {
									printMyCourses(email);
									successMenu();
									System.out.printf("Select an option: ");
									userChoice = input.nextInt();
									input.nextLine();
								}
								if (userChoice == 1) {
									printAllCourses();
									System.out.printf("\nEnter course ID: ");
									try {
										userChoice = input.nextInt();
										input.nextLine();
										if (studentService.getCourseById(userChoice) == null) {
											userChoice = 1;
											skipInput = true;
										} else {
											studentService.registerStudentToCourse(email, userChoice);
											printMyCourses(email);

											System.out.println("\nYou have been signed out.\n");
											isLoggedIn = false;
											userChoice = 2;
										}
									} catch (InputMismatchException e) {
										System.out.println("\nPlease enter a valid input.");
										input.nextLine();
									}
								} else if (userChoice == 2) {
									System.out.println("Logging out...");
									isLoggedIn = false;
								} else {
									System.out.println("\nPlease enter a valid input.");
									userChoice = 0;
									skipInput = false;
									;
								}
							} catch (InputMismatchException e) {
								System.out.println("\nPlease enter a valid input.");
								choice = 1;
								input.nextLine();
							}
						}
					}

				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter a valid input.\n\n");
				input.nextLine();
				choice = 0;
			}

		}
		input.close();

	}

	public static void menu() {
		System.out.println("Press 1 to log in, or press 2 to exit:\n");
		System.out.println("1. Log in as Student");
		System.out.println("2. Exit \n");
	}

	public static void successMenu() {
		System.out.println("1. Register to Class");
		System.out.println("2. Logout");
	}

	public static void printMyCourses(String email) {
		StudentService studentService = new StudentService();
		List<Course> studentCourses = studentService.getStudentCourses(email);

		System.out.println("\n\nMy Courses:\n");
		System.out.printf("%-4s%-28s %-16s\n", "ID", "COURSE NAME", "INSTRUCTOR NAME");
		System.out.println("==================================================");

		for (Course course : studentCourses) {
			System.out.printf("%-4s%-28s %-16s\n", course.getId(), course.getName(), course.getInstructor());
		}
		System.out.println("");
	}

	public static void printAllCourses() {
		CourseService courseService = new CourseService();
		List<Course> allCourses = courseService.getAllCourses();

		System.out.println("\n\nAll Courses:\n");
		System.out.printf("%-3s %-30s %-20s \n\n", "ID", "COURSE NAME", "INSTRUCTOR NAME");

		for (Course course : allCourses) {
			System.out.printf("%-3d %-30s %-20s \n", course.getId(), course.getName(), course.getInstructor());
		}
	}

}
