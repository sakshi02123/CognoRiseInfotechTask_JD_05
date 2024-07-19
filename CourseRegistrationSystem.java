import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Course class representing a course with attributes
class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0; // Initially no students enrolled
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void enrollStudent() {
        enrolledStudents++;
    }

    public void dropStudent() {
        enrolledStudents--;
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode + ", Title: " + title + ", Description: " + description +
                ", Capacity: " + capacity + ", Schedule: " + schedule +
                ", Enrolled Students: " + enrolledStudents + "/" + capacity;
    }
}

// Student class representing a student with attributes
class Student {
    private String studentId;
    private String name;
    private ArrayList<String> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(String courseCode) {
        registeredCourses.add(courseCode);
    }

    public void dropCourse(String courseCode) {
        registeredCourses.remove(courseCode);
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + ", Name: " + name + ", Registered Courses: " + registeredCourses;
    }
}

// CourseRegistrationSystem class to manage courses and student registrations
public class CourseRegistrationSystem {
    private ArrayList<Course> courses;
    private HashMap<String, Student> students;

    public CourseRegistrationSystem() {
        this.courses = new ArrayList<>();
        this.students = new HashMap<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public Course getCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null; // Course not found
    }

    public void addStudent(Student student) {
        students.put(student.getStudentId(), student);
    }

    public Student getStudentById(String studentId) {
        return students.get(studentId);
    }

    public void displayAllCourses() {
        System.out.println("\nAll Courses:");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public void displayAvailableCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses) {
            int availableSlots = course.getCapacity() - course.getEnrolledStudents();
            if (availableSlots > 0) {
                System.out.println(course);
            }
        }
    }

    public void registerCourse(String studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = getCourseByCode(courseCode);

        if (student == null) {
            System.out.println("Student not found with ID: " + studentId);
        } else if (course == null) {
            System.out.println("Course not found with code: " + courseCode);
        } else {
            int availableSlots = course.getCapacity() - course.getEnrolledStudents();
            if (availableSlots > 0) {
                student.registerCourse(courseCode);
                course.enrollStudent();
                System.out.println("Student " + student.getName() + " registered successfully for course " + course.getTitle());
            } else {
                System.out.println("Course " + course.getTitle() + " is full. Cannot register.");
            }
        }
    }

    public void dropCourse(String studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = getCourseByCode(courseCode);

        if (student == null) {
            System.out.println("Student not found with ID: " + studentId);
        } else if (course == null) {
            System.out.println("Course not found with code: " + courseCode);
        } else {
            if (student.getRegisteredCourses().contains(courseCode)) {
                student.dropCourse(courseCode);
                course.dropStudent();
                System.out.println("Student " + student.getName() + " dropped course " + course.getTitle());
            } else {
                System.out.println("Student " + student.getName() + " is not registered for course " + course.getTitle());
            }
        }
    }

    public static void main(String[] args) {
        CourseRegistrationSystem registrationSystem = new CourseRegistrationSystem();
        Scanner scanner = new Scanner(System.in);

        // Adding sample courses
        registrationSystem.addCourse(new Course("CSE101", "Introduction to Computer Science", "Fundamentals of programming", 30, "Mon/Wed/Fri 10:00 AM"));
        registrationSystem.addCourse(new Course("MAT201", "Calculus I", "Limits, derivatives, and integrals", 25, "Tue/Thu 9:00 AM"));
        registrationSystem.addCourse(new Course("PHY301", "Modern Physics", "Quantum mechanics and relativity", 20, "Tue/Fri 1:00 PM"));

        // Adding sample students
        registrationSystem.addStudent(new Student("S001", "Alice"));
        registrationSystem.addStudent(new Student("S002", "Bob"));
        registrationSystem.addStudent(new Student("S003", "Charlie"));

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Display All Courses");
            System.out.println("2. Display Available Courses");
            System.out.println("3. Register Student for Course");
            System.out.println("4. Drop Course for Student");
            System.out.println("5. Exit");

            int choice = getChoice(scanner);

            switch (choice) {
                case 1:
                    registrationSystem.displayAllCourses();
                    break;
                case 2:
                    registrationSystem.displayAvailableCourses();
                    break;
                case 3:
                    registerStudentForCourse(scanner, registrationSystem);
                    break;
                case 4:
                    dropCourseForStudent(scanner, registrationSystem);
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }
    }

    private static int getChoice(Scanner scanner) {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter your choice as a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void registerStudentForCourse(Scanner scanner, CourseRegistrationSystem registrationSystem) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.next();
        System.out.print("Enter course code: ");
        String courseCode = scanner.next();

        registrationSystem.registerCourse(studentId, courseCode);
    }

    private static void dropCourseForStudent(Scanner scanner, CourseRegistrationSystem registrationSystem) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.next();
        System.out.print("Enter course code: ");
        String courseCode = scanner.next();

        registrationSystem.dropCourse(studentId, courseCode);
    }
}