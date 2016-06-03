package model;

import java.sql.*;

public class JDBCConnector {
	private static Connection con;

	public JDBCConnector() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con =DriverManager.getConnection(" jdbc:postgresql://java.is.uni-due.de/ws1011", "ws1011",
					"ftpw10");
			//System.out.println("connected");
			System.out.println(con.getMetaData().getURL());
			System.out.println(con.getMetaData().getUserName());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		JDBCConnector test = new JDBCConnector();
		long id = test.insert("test", 250, 25);
		System.out.println(id);
		for (int i = 580; i < 800; i++) {
			Product testP = test.read(i);
			System.out.println(testP.getId());
			System.out.println(testP.toString());
		}
		
		
	}

	public long insert(String name, double price, int quantity) {
		
		String sql = "INSERT INTO products(name, price ,quantity) VALUES(?,?,?)";
		try {
			PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setDouble(2, price);
			stmt.setInt(3, quantity);
			if(stmt.executeUpdate()!=1){
				throw new SQLException();
			}
			try(ResultSet rs=stmt.getGeneratedKeys()){
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
		long id=insert(product.getName(),product.getPrice(),product.getQuantity());
		product.setId(id);
	}

	public Product read(long productId) {
		Product product = new Product();
		String sql = "SELECT id,name, price, quantity FROM products WHERE id=? ";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setLong(1, productId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getLong("price"));
				product.setQuantity(rs.getInt("quantity"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} 
			return product;
		

	}

}