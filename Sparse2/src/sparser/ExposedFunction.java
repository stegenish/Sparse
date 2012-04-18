package sparser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExposedFunction extends Function {
	private Method method;

	ExposedFunction(ExposedSparseFunction exposedFunction, Method method) {
		super(exposedFunction.name());
		this.method = method;
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Entity object = args.next();
		Object[] parameters = createParameters(args);
		
		return callMethodOnObject(object, parameters);
	}

	private Object[] createParameters(ArgumentList args) {
		Object[] objects = new Object [getNumberOfParameters()];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = args.next();
		}
		return objects;
	}

	private int getNumberOfParameters() {
		return method.getParameterTypes().length;
	}
	
	private Entity callMethodOnObject(Entity object, Object[] parameters) {
		try {
			return (Entity) method.invoke(object, parameters);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return SparseNull.theNull;
	}

	
}