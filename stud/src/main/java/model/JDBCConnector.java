package model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.sql.RowSet;

import fpt.com.db.*;

public class JDBCConnector extends AbstractDatabaseStrategy {

	private static Connection con;
	private long id;
	private ArrayList<Product> products;

	public JDBCConnector() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con =DriverManager.getConnection("jdbc:postgresql://java.is.uni-due.de/ws1011",
					"ws1011", "ftpw10");
			//System.out.println("connected");
			System.out.println(con.getMetaData().getURL());
			System.out.println(con.getMetaData().getUserName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		id = 0;
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
		ResultSet rs = null ;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return product;
	}
	public void readProducts(int max){
		ResultSet rs = null ;
		products = new ArrayList<Product>();
		String sql = "SELECT * FROM products LIMIT "+max;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			//stmt.setMaxRows(max);
			rs = (ResultSet) stmt.executeQuery();
			while (rs.next()) {
				products.add(new Product(rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity")));
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs  != null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public Product readObject() throws IOException {
		if(products==null){
			readProducts(90);
		}
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