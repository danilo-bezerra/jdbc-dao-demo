package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			res = st.executeQuery("SELECT * FROM seller");
			
			while (res.next()) {
				System.out.println(res.getString("name") + ", " + res.getString("email"));
			}
			
		
		
			
		} catch (SQLException e){
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(res);
			DB.closeConnection();
		}

	}

}
