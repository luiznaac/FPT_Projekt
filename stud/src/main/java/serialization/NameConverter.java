package serialization;

import com.thoughtworks.xstream.converters.SingleValueConverter;

import javafx.beans.property.SimpleStringProperty;

public class NameConverter implements SingleValueConverter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(SimpleStringProperty.class);
	}

	@Override
	public String toString(Object obj) {
		return ((SimpleStringProperty)obj).get();
	}

	@Override
	public Object fromString(String str) {
		return new SimpleStringProperty(str);
	}

}
