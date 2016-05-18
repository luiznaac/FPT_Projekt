package serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import model.Product;
import model.ProductList;

public class XStreamStrategy implements fpt.com.SerializableStrategy {

	XStream xstream;
	InputStream input;
	OutputStream output;
	IDGenerator idgenerator;

	public XStreamStrategy(String path) {
		xstream = createXStream(Product.class);
		idgenerator = new IDGenerator();
		try{
			open(path);
		}catch(IOException ex){
			System.out.println(ex + " at serialization.XStreamStrategy.XStreamStrategy");
		}
	}

	@Override
	public fpt.com.Product readObject() throws IOException {
		Product read = null;
		try{
			read = (Product) xstream.fromXML(input);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return read;
	}

	@Override
	public void writeObject(fpt.com.Product obj) throws IOException {
		try{
			obj.setId(idgenerator.getId());
			xstream.toXML(obj, output);
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
		this.input = input;
		this.output = output;

		xstream.alias("waren", ProductList.class);
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

	public ProductList readList(){
		ProductList read = new ProductList();
		if(input != null){
			try{
				read = (ProductList) xstream.fromXML(input);
			}catch(Exception ex){
				System.out.println("Nothing to load");
			}
		}
		writeList(read);
		return read;
	}

	public void writeList(ProductList products){
		IDGenerator idgen = new IDGenerator();
		for(fpt.com.Product p : products){
			try{
				((Product)p).setId(idgen.getId());
			}catch(IDOverflowException ex){
				System.out.println(ex);
			}
		}

		try{
			xstream.toXML((ArrayList)products, output);
		}catch(Exception ex){
			System.out.println(ex + " at serialization.BinaryStrategy.serializeList");
		}
	}

}
