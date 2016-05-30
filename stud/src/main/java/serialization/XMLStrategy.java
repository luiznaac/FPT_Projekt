package serialization;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import model.Product;
import model.ProductList;

public class XMLStrategy implements fpt.com.SerializableStrategy {

	private XMLEncoder encoder;
	private XMLDecoder decoder;

	public XMLStrategy(String path){
		try {
			open(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Product readObject() throws IOException {
		Product read = new Product();
//		try{
			read = (Product)decoder.readObject();
//		}catch(Exception ex){
//			System.out.println("Nothing to load");
//		}
		return read;
	}

	@Override
	public void writeObject(fpt.com.Product obj) throws IOException {
		try{
			encoder.writeObject(obj);
		}catch(Exception ex){
			System.out.println(ex);
		}
	}

	@Override
	public void close() throws IOException {
		if(decoder != null)
			decoder.close();
		encoder.flush();
		encoder.close();
	}

	@Override
	public void open(InputStream input, OutputStream output) throws IOException {
		if(input != null)
			decoder = new XMLDecoder(input);
		else
			decoder = null;
		encoder = new XMLEncoder(output);
	}

}
