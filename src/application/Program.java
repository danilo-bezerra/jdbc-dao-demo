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
		
		System.out.println("=== TEST | Seller - insert ===");
		Department dep1 = new Department(2, null);
		Seller seller1 = new Seller(null,"Alfred", "alfred@email.com", LocalDate.parse("1985-05-08"), 3200.0, dep1);
		sellerDAO.insert(seller1);
		System.out.println("Seller1: " + seller1);
		
		System.out.println("=== TEST | Seller - update ===");
		Department dep2 = new Department(2, null);
		Seller seller2 = new Seller(15,"Alfred Orange", "alfred_coder@email.com", LocalDate.parse("1985-05-08"), 3900.0, dep2);
		sellerDAO.update(seller2);
		System.out.println();
		
		System.out.println("=== TEST | Seller - findById ===");
		Seller seller = sellerDAO.findById(3);
		System.out.println(seller);
		
		System.out.println();
		
		System.out.println("=== TEST | Seller - findByDepartment ===");
		List<Seller> sellers = sellerDAO.findByDepartament(seller.getDepartment());
		sellers.forEach(System.out::println);
		
		System.out.println();
		
		System.out.println("=== TEST | Seller - findAll ===");
		List<Seller> allSellers = sellerDAO.findAll();
		allSellers.forEach(System.out::println);
		
		
		//System.out.println(Date.valueOf(LocalDate.parse("1985-05-09")));
		
		
		
		

	}

}
