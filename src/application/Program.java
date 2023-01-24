package application;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import model.dao.DAO;
import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
//		Statement st = null;
//		ResultSet res = null;
//		try (Connection conn = DB.getConnection();){
//			st = conn.createStatement();
//			res = st.executeQuery("SELECT * FROM seller");
//
//			while (res.next()) {
//				System.out.println(res.getString("name") + ", " + res.getString("email"));
//			}
//
//		} catch (SQLException e) {
//			throw new DbException(e.getMessage());
//		} finally {
//			DB.closeStatement(st);
//			DB.closeResultSet(res);
//			DB.closeConnection();
//			
//		}
		SellerDAO sellerDAO = DAOFactory.createSellerDAO();
		DAO<Department> departmentDAO = DAOFactory.createDepartmentDAO();
		
		
		System.out.println("=== TEST | Seller - findById ===");
		Seller seller = sellerDAO.findById(3);
		System.out.println(seller);
		
		System.out.println();
		
		System.out.println("=== TEST | Seller - findByDepartment ===");
		List<Seller> sellers = sellerDAO.findByDepartament(seller.getDepartment());
		sellers.forEach(System.out::println);
		
		
		//System.out.println(Date.valueOf(LocalDate.parse("1985-05-09")));
		
		
		
		

	}

}
