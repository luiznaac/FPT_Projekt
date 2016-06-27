package model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import fpt.com.db.*;

public class JDBCConnector extends AbstractDatabaseStrategy {

	private static Connection con;
	private ArrayList<Product> products; //ArrayList mit gelesenen Produkten von DB

	public JDBCConnector() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection("jdbc:postgresql://java.is.uni-due.de/ws1011",
					"ws1011", "ftpw10");
			System.out.println(con.getMetaData().getURL());
			System.out.println(con.getMetaData().getUserName());
			//zeigt alle Tabellen der Datenbank an
			String[] types = {"TABLE"};
			ResultSet rsTables = con.getMetaData().getTables(null, null, "%", types);
			while(rsTables.next())
				System.out.println(rsTables.getString("TABLE_NAME"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public long insert(String name, double price, int quantity) {
		String sql = "INSERT INTO products(name, price, quantity) VALUES(?,?,?)";
		try {
			PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setDouble(2, price);
			stmt.setInt(3, quantity);
			if(stmt.executeUpdate() != 1){
				throw new SQLException();
			}
			try(ResultSet rs = stmt.getGeneratedKeys()){
				while(rs.next()){
					return rs.getLong(1);
				}
			};
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void insert(Product product) {
		long id = insert(product.getName(), product.getPrice(), product.getQuantity());
		product.setId(id);
	}

	public Product read(long productId) {
		Product product = new Product();
		ResultSet rs = null;
		String sql = "SELECT id, name, price, quantity FROM products WHERE id=?";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setLong(1, productId);
			stmt.setMaxRows(20);
			rs = stmt.executeQuery();
			while (rs.next()) {
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getLong("price"));
				product.setQuantity(rs.getInt("quantity"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs  != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return product;
	}

	public void readProducts(int max){
		ResultSet rs = null;
		products = new ArrayList<Product>();
		String sql = "SELECT * FROM products LIMIT ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, max);
			rs = (ResultSet) stmt.executeQuery();
			while (rs.next()) {
				products.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Product readObject() throws IOException {
		if(products == null)
			readProducts(50);
		return products.remove(0);
	}

	@Override
	public void writeObject(fpt.com.Product obj) throws IOException {
		insert((Product)obj);
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public void open() throws IOException {
	}
}