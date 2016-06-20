package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.openjpa.persistence.OpenJPAPersistence;
import fpt.com.db.AbstractDatabaseStrategy;

public class OpenJPAConnector extends AbstractDatabaseStrategy {

	EntityManager mang;
	EntityTransaction trans;
	private ArrayList<Product> products; //ArrayList mit gelesenen Produkten von DB

	public OpenJPAConnector() {
		startWithConfiguration();
	}

	public void startWithoutConfiguration() {
		Map<String, String> map = new HashMap<String, String>();

		map.put("openjpa.ConnectionURL", "jdbc:postgresql://java.is.uni-due.de/ws1011");
		map.put("openjpa.ConnectionDriverName", "org.postgresql.Driver");
		map.put("openjpa.ConnectionUserName", "ws1011");
		map.put("openjpa.ConnectionPassword", "ftpw10");
		map.put("openjpa.RuntimeUnenhancedClasses", "supported");
		map.put("openjpa.jdbc.SynchronizeMappings", "false");
		map.put("openjpa.MetaDataFactory", "jpa(Types = model.Product)");

		mang = OpenJPAPersistence.getEntityManagerFactory(map).createEntityManager();
		trans = mang.getTransaction();
	}

	//persistence.xml liegt auf /main/resources/META-INF
	public void startWithConfiguration() {
		mang = (Persistence.createEntityManagerFactory("hardwareshop")).createEntityManager();
		trans = mang.getTransaction();
	}

	public void close(){
		mang.close();
	}

	public void insert(Product product) {
		trans.begin();
		mang.persist(product);
		trans.commit();
	}

	public Product read(long id) {
		Product pread;
		String sql = String.format("SELECT p FROM Product p WHERE p.id = %d", id);

		trans.begin();
		pread = (Product)(mang.createQuery(sql).getResultList()).get(0);
		trans.commit();

		return pread;
	}

	public void readProducts(int max) {
		String sql = "SELECT p FROM Product p";

		trans.begin();
		products = new ArrayList<>(mang.createQuery(sql).setMaxResults(max).getResultList());
		trans.commit();
	}

	@Override
	public Product readObject() throws IOException {
		if(products == null)
			readProducts(50);
		return products.remove(0);
	}

	@Override
	public void writeObject(fpt.com.Product obj) throws IOException {
		insert((Product) obj);
	}

	@Override
	public void open() throws IOException {
	}

}