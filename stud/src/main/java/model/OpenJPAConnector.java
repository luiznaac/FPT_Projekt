package model;

import java.util.*;

import javax.persistence.*;

import org.apache.openjpa.persistence.OpenJPAPersistence;

public class OpenJPAConnector {
	public static void main(String[] args) {
		OpenJPAConnector m = new OpenJPAConnector();
		m.test();
	}

	private void test() {
		initialize();
		Product p;
		p = read(100);
		for (Product pr : read()) {
			System.out.println(pr.getName());
		}
	}

	EntityManagerFactory fact;
	EntityManager mang;
	EntityTransaction trans;

	public void initialize() {
		fact = getWithoutConfig();
		mang = fact.createEntityManager();
		trans = mang.getTransaction();
		trans.begin();
	}

	public void close() {
		trans.commit();
		if (mang != null) {
			mang.close();
		}
		if (fact != null) {
			fact.close();
			fact = null;
		}
	}

	public void write(Product product) {
		product.setId(0);
		mang.persist(product);
	}

	public Product read(long id) {
		String sql = String.format("SELECT p FROM  Product p WHERE p.id =%d", id);
		@SuppressWarnings("unchecked")
		List<Product> result = (List<Product>) mang.createQuery(sql).getResultList();
		if (!result.isEmpty())
			return result.get(0);
		else
			return null;
	}

	public List<Product> read() {
		String sql = String.format("SELECT p FROM  Product p ORDER BY p.id DESC");
		@SuppressWarnings("unchecked")
		Query q = mang.createQuery(sql);
		q.setMaxResults(20);
		List<Product> result = (List<Product>) q.getResultList();
		return result;
	}

	public void insert(Product product) {
		mang.persist(product);
	}

	public EntityManagerFactory getWithoutConfig() {

		Map<String, String> map = new HashMap<String, String>();

		map.put("openjpa.ConnectionURL", "jdbc:postgresql://java.is.uni-due.de/ws1011");
		map.put("openjpa.ConnectionDriverName", "org.postgresql.Driver");
		map.put("openjpa.ConnectionUserName", "ws1011");
		map.put("openjpa.ConnectionPassword", "ftpw10");
		map.put("openjpa.RuntimeUnenhancedClasses", "supported");
		map.put("openjpa.jdbc.SynchronizeMappings", "false");

		List<Class<?>> types = new ArrayList<Class<?>>();
		types.add(Product.class);

		if (!types.isEmpty()) {
			StringBuffer buffer = new StringBuffer();
			for (Class<?> classObject : types) {
				if (buffer.length() > 0) {
					buffer.append(";");
				}
				buffer.append(classObject.getName());
			}
			map.put("openjpa.MetaDataFactory", "jpa(Types=" + buffer.toString() + ")");
		}
		return OpenJPAPersistence.getEntityManagerFactory(map);
	}

}
