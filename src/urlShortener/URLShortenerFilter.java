package urlShortener;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.jdbc.MysqlDataSource;

public class URLShortenerFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String res = req.getRequestURI();
		
		String[] resSplit = res.split("[/_]", 0);
		
		if (!resSplit[2].equals("curlec")) {
			RequestDispatcher rd = request.getRequestDispatcher(resSplit[2]);
			rd.forward(request, response);
			return;
		}
		
		int resBase10 = utils.Base62.convertToBase10(resSplit[3]);
		
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("sql12352933");
		dataSource.setPassword("FT6aYGn9M4");
		dataSource.setServerName("sql12.freemysqlhosting.net");
		
		Connection con;
		PreparedStatement pstmt;
		ResultSet rs;
		
		try {
			con = dataSource.getConnection();
		
			pstmt = con.prepareStatement("SELECT * FROM sql12352933.url WHERE id = ?");
			pstmt.setString(1, Integer.toString(resBase10));
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				res = rs.getString("url");
			}
			
			con.close();
			pstmt.close();
			rs.close();
			
			if (res != null) {
				resp.sendRedirect(utils.URLValidator.buildURL(res));
			} else {
				response.getWriter().println("Invalid URL!");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
