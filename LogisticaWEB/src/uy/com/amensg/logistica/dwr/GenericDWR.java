package uy.com.amensg.logistica.dwr;

import java.lang.reflect.Method;
import java.util.Date;

public class GenericDWR<T1, T2> {
	
	public T2 transform(T1 entity) {
		@SuppressWarnings("unchecked")
		T2 result = (T2) new Object();
		
		for (Method method : entity.getClass().getDeclaredMethods()) {
			String methodName = method.getName();
			if (methodName.startsWith("get")) {
//				Object getResult = method.invoke(entity, new Object[]{});
				
				String setterMethodName = "set" + methodName.substring(3);
				
				for (Method methodSetter : result.getClass().getDeclaredMethods()) {
					if (methodSetter.getName().equals(setterMethodName)) {
//						methodSetter.invoke(result, transform(getResult));
					}
				}
			}
		}
			
		return result;
	}
	
	public static Long transform(Long field) {
		return field;
	}
	
	public static String transform(String field) {
		return field;
	}
	
	public static Date transform(Date field) {
		return field;
	}
}