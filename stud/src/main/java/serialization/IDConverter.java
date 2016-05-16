package serialization;

import com.thoughtworks.xstream.converters.SingleValueConverter;

public class IDConverter implements SingleValueConverter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(long.class);
	}

	@Override
	public String toString(Object obj) {
		String value = String.valueOf(obj);
		int length = value.length();
		for(int i = 0 ; i < 6 - length ; i++){
			value = "0" + value;
		}
		return value;
	}

	@Override
	public Object fromString(String str) {
		return Long.parseLong(str);
	}

}
