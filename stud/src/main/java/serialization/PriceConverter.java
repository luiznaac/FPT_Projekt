package serialization;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.thoughtworks.xstream.converters.SingleValueConverter;

import javafx.beans.property.SimpleDoubleProperty;

public class PriceConverter implements SingleValueConverter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(SimpleDoubleProperty.class);
	}

	@Override
	public String toString(Object obj) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("0.00", symbols);
		return df.format(((SimpleDoubleProperty)obj).get());
	}

	@Override
	public Object fromString(String str) {
		return new SimpleDoubleProperty(Double.parseDouble(str));
	}

}
