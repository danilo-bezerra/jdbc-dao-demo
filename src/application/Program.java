package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import db.DB;
import db.DbException;
import model.dao.DAO;
import model.dao.DAOFacatory;
import model.entities.Department;
import model.entities.Seller;

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

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(res);
			DB.closeConnection();
		}
		DAO<Seller> sellerDAO = DAOFacatory.createSellerDAO();
		DAO<Department> departmentDAO = DAOFacatory.createDepartmentDAO();
		
		
		System.out.println("=== TEST | Seller - findById ===");
		Seller seller = sellerDAO.findById(3);
		
		System.out.println(seller);
		System.out.println(Date.valueOf(LocalDate.parse("1985-05-09")));
		
		
		
		

	}

}
