package serialization;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fpt.com.Product;

public class XMLStrategy implements fpt.com.SerializableStrategy {

	private XMLEncoder encoder;
	private XMLDecoder decoder;

	public XMLStrategy(){
		try {
			open("products.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Product readObject() throws IOException {
		Product read = (Product)decoder.readObject();
		return read;
	}

	@Override
	public void writeObject(Product obj) throws IOException {
		encoder.writeObject(obj);
		encoder.flush();
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

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
