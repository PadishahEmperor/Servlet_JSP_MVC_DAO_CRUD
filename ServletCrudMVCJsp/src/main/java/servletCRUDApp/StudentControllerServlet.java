package servletCRUDApp;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;




@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDBUtil studentDBUtil; // Has inside List<Student> Method which this servlet will call;
	
	@Resource(name="jdbc/web_student_tracker") // initialisig inside the constructor, java dataSource injection/ Conn pool/ DataSource, Tomcat servel will inject that connection pool object and assign it to the varriable dataSource.   
	private DataSource dataSource;
	
	// Now i want to Initialise this StudentDBUtil from the Constructor
	
	// We'll use init method, that method is provided by the parent class and will be called by Java EE server or Tomcat when the server is first loaded or initialized.
	
	// So good place to put our own custom initialization work
	
	// Also, this is part of the API
	
	@Override
	public void init() throws ServletException {
		super.init();
		// create our student db util... and pass in the conn pool/ datasource.
		try {
			this.studentDBUtil = new StudentDBUtil(this.dataSource);
		}catch(Exception e) {
			throw new ServletException(e);
			//e.printStackTrace();
		}
		
		// So again, the work that you would normally do on a constructor when you work with servlets, you place that code in init method. Which we inherit from the generic servlet and we can override.
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// list the students... in the MVC fashion.
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			//if command is missing then default to listing the students
			if(theCommand== null) {
				theCommand = "LIST";
			}
			
			
			// route to the appropriate method
			switch(theCommand) {
			case "LIST":
				// list the students in MVC fashion
				listStudents(request,response);
				break;
			case "ADD":
				addStudent(request,response);
				break;
			case "LOAD":
				//LOAD COMMAND
				loadStudent(request,response);
				break;
			case "UPDATE":
				// CODE for UPDATE
				updateStudent(request,response);
				break;
			case "DELETE":
				//CODE for DELETE
				deleteStudent(request,response);
				break;
				
			//if nothing matches then default option would be listing all the students from the database	
			default:
				listStudents(request,response);
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException(e);
			//e.printStackTrace();
		}
		
		//request.setAttribute("MY_STUDENTS", studends);
		
		
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String stId = request.getParameter("studentId");
		this.studentDBUtil.deleteStudent(stId);
		listStudents(request, response);
		
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from form data
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String studentId = request.getParameter("studentId");
		
		
		this.studentDBUtil.updateStudent(studentId, firstName,lastName,email);
		listStudents(request, response);
		
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		// TODO Auto-generated method stub
		String stId = request.getParameter("studentId");
		
		Student theStudent = this.studentDBUtil.loadStudent(stId);
		
		request.setAttribute("MY_STUDENT", theStudent);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student.jsp"); // Location where you want to forward the request
		
		dispatcher.forward(request, response);
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// read student info from form data
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		
		// create a new student object
		Student newStudent = new Student(firstName, lastName, email);
		
		// add the student to the database
		this.studentDBUtil.addStudent(newStudent);
		
		// send back to main page(list-students)
		listStudents(request,response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//get students from db util
		List<Student> students = studentDBUtil.listStudents();
		System.out.println(students);
		//add students to the request
		request.setAttribute("MY_STUDENT_LIST", students);
		
		//send to JSP page (View)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp"); // Loaction of where you want to dispatch the request!
		
		//Forward the request
		
		dispatcher.forward(request, response);
		
		
	}

	

}
