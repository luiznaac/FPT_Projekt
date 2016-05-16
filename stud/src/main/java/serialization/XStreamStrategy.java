package serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.thoughtworks.xstream.XStream;
import model.Product;

public class XStreamStrategy implements fpt.com.SerializableStrategy {

	XStream xstream;
	InputStream input;
	OutputStream output;
	IDGenerator idgenerator;

	public XStreamStrategy() {
		xstream = createXStream(Product.class);
		idgenerator = new IDGenerator();
		try{
			open("products.xml");
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
		// TODO Auto-generated method stub
	}

	@Override
	public void open(InputStream input, OutputStream output) throws IOException {
//		DomDriver driver = new DomDriver();
//		if(input != null)
//			driver.createReader(input);
//		driver.createWriter(output);
//		this.xstream = new XStream(driver);
		this.input = input;
		this.output = output;
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
