package serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import com.thoughtworks.xstream.XStream;
import model.Product;

public class XStreamStrategy implements fpt.com.SerializableStrategy {

	private ObjectInputStream input;
	private ObjectOutputStream output;

	public XStreamStrategy(String path) {
		try{
			open(path);
		}catch(IOException ex){
			System.out.println(ex + " at serialization.XStreamStrategy.XStreamStrategy");
		}
	}

	@Override
	public Product readObject() throws IOException {
		Product read = null;
			try {
				read = (Product) input.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return read;
	}

	@Override
	public void writeObject(fpt.com.Product obj) throws IOException {
		try{
			output.writeObject((Product)obj);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		if(input != null)
			input.close();
		output.close();
	}

	@Override
	public void open(InputStream input, OutputStream output) throws IOException {
		XStream xstream = createXStream(Product.class);
		if(input == null){
			this.input = null;

		}
		else
			this.input = xstream.createObjectInputStream(input);
		this.output = xstream.createObjectOutputStream(output, "waren");

		xstream.alias("ware", Product.class);
		xstream.aliasField("name", Product.class, "name");
		xstream.aliasField("preis", Product.class, "price");
		xstream.aliasField("anzahl", Product.class, "quantity");
		xstream.useAttributeFor(Product.class, "id");
		xstream.registerConverter(new IDConverter());
		xstream.registerConverter(new NameConverter());
		xstream.registerConverter(new PriceConverter());
		xstream.registerConverter(new QuantityConverter());
	}

}
