package serialization;

import com.thoughtworks.xstream.converters.SingleValueConverter;

import javafx.beans.property.SimpleIntegerProperty;

public class QuantityConverter implements SingleValueConverter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(SimpleIntegerProperty.class);
	}

	@Override
	public String toString(Object obj) {
		return String.valueOf(((SimpleIntegerProperty)obj).get());
	}

	@Override
	public Object fromString(String str) {
		return new SimpleIntegerProperty(Integer.parseInt(str));
	}

}
