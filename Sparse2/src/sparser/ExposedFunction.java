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
		Entity object = getObject(args);
		Object[] parameters = createParameters(args);
		Object returnValue = callMethodOnObject(object, parameters);
		return toEntity(returnValue);
	}

	private Entity getObject(ArgumentList args) {
		Entity object = args.nextCast(method.getDeclaringClass());
		return object;
	}

	private Entity toEntity(Object returnValue) {
		return (Entity) returnValue;
	}

	private Object[] createParameters(ArgumentList args) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		Object[] objects = new Object [parameterTypes.length];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = args.nextCast(parameterTypes[i]);
		}
		return objects;
	}

	private Object callMethodOnObject(Entity object, Object[] parameters) {
		try {
			return method.invoke(object, parameters);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.getMessage();
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return SparseNull.theNull;
	}
}