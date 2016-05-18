package serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import model.ProductList;
import model.Product;

public class BinaryStrategy implements fpt.com.SerializableStrategy {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public BinaryStrategy(String path){
		try{
			open(path);
		}catch(IOException ex){
			System.out.println(ex + " at serialization.BinaryStrategy.BinaryStrategy");
		}
	}

	@Override
	public Product readObject() throws IOException {
		Product read = new Product();
		if(ois != null){
			try{
				read = (Product)ois.readObject();
			}catch(ClassNotFoundException ex){
				System.out.println(ex + " at serialization.BinaryStrategy.readObject");
			}
		}
		return read;
	}

	@Override
	public void writeObject(fpt.com.Product obj) throws IOException {
		oos.writeObject(obj);
		oos.flush();
	}

	@Override
	public void close() throws IOException {
		if(ois != null)
			ois.close();
		oos.close();
	}

	@Override
	public void open(InputStream input, OutputStream output) throws IOException {
		if(input == null)
			ois = null;
		else
			this.ois = new ObjectInputStream(input);
		this.oos = new ObjectOutputStream(output);
	}

	public ProductList readList(){
		ProductList read = new ProductList();
		if(ois != null){
			try{
				read = (ProductList)ois.readObject();
			}catch(ClassNotFoundException ex){
				System.out.println(ex + " at serialization.BinaryStrategy.deserializeList");
			}catch(IOException ex){
				System.out.println("Nothing to load");
			}
		}
		writeList(read);
		return read;
	}

	public void writeList(ProductList products){
		try{
			oos.writeObject(products);
			oos.flush();
		}catch(IOException ex){
			System.out.println(ex + " at serialization.BinaryStrategy.serializeList");
		}
	}

}
