package urlShortener;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * Servlet implementation class InsertURL
 */
@WebServlet("/InsertURL")
public class InsertURL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertURL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		
		response.setContentType("text/html");
		
		if (!utils.URLValidator.validateURL(url)) {
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			response.getWriter().println("Please enter a valid URL!");
			rd.include(request, response);
		} else {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("sql12352933");
			dataSource.setPassword("FT6aYGn9M4");
			dataSource.setServerName("sql12.freemysqlhosting.net");
			
			Connection con;
			PreparedStatement pstmt;
			Statement stmt;
			ResultSet rs;
			
			try {
				con = dataSource.getConnection();
			
				pstmt = con.prepareStatement("INSERT INTO sql12352933.url (url) VALUES (?)");
				pstmt.setString(1, url);
				
				pstmt.executeUpdate();
				
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
				
				rs.next();	
				
				response.getWriter().println(url + " Successfully inserted!");
				
				con.close();
				pstmt.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
