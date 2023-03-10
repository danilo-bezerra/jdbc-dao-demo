package model.dao.implementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAOJDBC implements SellerDAO {
	private Connection conn;

	public SellerDAOJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		String sql = "INSERT INTO seller"
				+ " (name, email, birthDate, baseSalary, departmentId)"
				+ " VALUES "
				+ " (?,?,?,?,?)";
		 try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 stmt.setString(1, obj.getName());
			 stmt.setString(2, obj.getEmail());
			 stmt.setDate(3, Date.valueOf(obj.getBirthDate()));
			 stmt.setDouble(4, obj.getBaseSalary());
			 stmt.setInt(5, obj.getDepartment().getId());
			 
			 int rowCount = stmt.executeUpdate();
			 
			 if (rowCount < 1) {
				 throw new DbException("No rows affected");
			 }
			 ResultSet rs = stmt.getGeneratedKeys();
			 if (rs.next()) {
				 obj.setId(rs.getInt(1));
			 }
			 rs.close();
			 
			 
		 } catch (SQLException e) {
			 throw new DbException(e.getMessage());
		 }

	}

	@Override
	public void update(Seller obj) {
		String sql = ("UPDATE seller"
				+ " SET name = ? ,email = ?, birthDate = ?, baseSalary = ?, departmentId = ?"
				+ " WHERE id = ?"
				);
			
		 try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			 stmt.setString(1, obj.getName());
			 stmt.setString(2, obj.getEmail());
			 stmt.setDate(3, Date.valueOf(obj.getBirthDate()));
			 stmt.setDouble(4, obj.getBaseSalary());
			 stmt.setInt(5, obj.getDepartment().getId());
			 stmt.setInt(6, obj.getId());
			 
			 stmt.execute();
			 
		 } catch (SQLException e) {
			 throw new DbException(e.getMessage());
		 }
	}

	@Override
	public void deleteById(Integer id) {
		String sql = "DELETE FROM seller WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);

			stmt.execute();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public Seller findById(Integer id) {
		String sql = "SELECT seller.*, department.name as depName " + "FROM seller INNER JOIN department "
				+ "ON seller.departmentId = department.id " + "WHERE seller.id = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = instantianteDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);
				return seller;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	public List<Seller> findByDepartament(Department dep) {
		String sql = "SELECT seller.*, department.name as depName " + "FROM seller INNER JOIN department "
				+ "ON department.id = seller.departmentid " + "WHERE departmentId = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, dep.getId());
			rs = stmt.executeQuery();
			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				Department department = map.get(rs.getInt("departmentId"));
				if (department == null) {
					department = instantianteDepartment(rs);
					map.put(department.getId(), department);
				}
				sellers.add(instantiateSeller(rs, department));
			}
			return sellers;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("id"));
		seller.setName(rs.getString("name"));
		seller.setEmail(rs.getString("email"));
		seller.setBirthDate(rs.getDate("birthDate").toLocalDate());
		seller.setBaseSalary(rs.getDouble("baseSalary"));
		seller.setDepartment(dep);
		return seller;
	}

	private Department instantianteDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("departmentId"));
		dep.setName(rs.getString("depName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		String sql = "SELECT seller.*, department.name AS depName  FROM seller INNER JOIN department ON department.id = seller.departmentId ORDER BY name";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("departmentId"));

				if (dep == null) {
					dep = instantianteDepartment(rs);
					map.put(dep.getId(), dep);
					System.out.println("put");
				}

				sellers.add(instantiateSeller(rs, dep));
			}

			return sellers;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

}
