package servletCRUDApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {
	private DataSource dataSource;
	public StudentDBUtil(DataSource theDataSource) {
		this.dataSource = theDataSource;
	}
	
	public List<Student> listStudents() throws Exception{
		//DAO-Data Access Object;
		
		List<Student> students = new ArrayList<>();
		
		
		//Connection Pool Object that Tomcat will inject with the Annotation
		
		//JDBC Connection Objects;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			//Steps: get connection
			con = dataSource.getConnection();
			
			String sql = "Select * from students;";
			
			st = con.createStatement();
			
			rs = st.executeQuery(sql);
			
			// execute query
			while(rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				
				//Create new Student Object;
				Student tempStudent = new Student(id, firstName, lastName, email);
				
				//add it to the list
				students.add(tempStudent);
			}
			return students;
			
		} 
			finally {
				//close JDBC objects
				close(con,st,rs);
			}
		
		
		
		
		
	}

	private void close(Connection con, Statement st, ResultSet rs) {
		// TODO Auto-generated method stub
		//Close Sql objects
		try {
			if(rs != null) {
				rs.close();
			}
			
			if(st != null) {
				st.close();
			}
			
			if(con != null) {
				con.close();
			}
			
			
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	// newStudent is the student which will be passed into this method in the Servlet container
	public void addStudent(Student newStudent) throws SQLException {
		
		//create sql for insert
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			//set the parameter value for the student
			con = dataSource.getConnection();
			
			//create sql insert
			String sql = "Insert into students "
						+ "(first_name, last_name, email)"
						+ "values(?,?,?)"; // Prepared statemenst are based off of placeholders and you fill in those placeholders or the param values with the actual values/data
			
			ps = con.prepareStatement(sql);
			
			//set the param values for the student and the param values are 1 based, everthing in java is 0 based.
			ps.setString(1, newStudent.getFirstName());
			ps.setString(2, newStudent.getLastName());
			ps.setString(3,newStudent.getEmail());
			
			
			
			ps.execute();
			
		}finally {
			//clean up JDBC objects
			close(con, ps,null);
		}
	
	}

	public Student loadStudent(String stId) throws SQLException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(stId);
		Student newStudent = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {

			String sql = "Select * from students where id=?";
			
			con = dataSource.getConnection();
			
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				
				newStudent = new Student(id, firstName, lastName, email);
			}
		}finally {
			close(con,ps,rs);
		}
		
		
		return newStudent;
		
	}

	public void updateStudent(String studentId, String firstName, String lastName, String email) throws SQLException {
		
		int id = Integer.parseInt(studentId);
		//Student newStudent = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "Update students Set first_name=?, last_name=?, email=? Where id=?";
		
		try {
			
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, email);
			ps.setInt(4, id);
			
			ps.execute();
			
		}finally {
			close(con,ps,rs);
		}
		
	}

	public void deleteStudent(String stId) throws SQLException {

		int id = Integer.parseInt(stId);
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "Delete from students where id=?";
		
		try {
			
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			
			
			ps.setInt(1, id);
			
			ps.execute();
			
		}finally {
			close(con,ps,rs);
		}
		
	}
}
