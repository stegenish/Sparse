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
		Object returnValue = callMethodOnObject(object, parameters);
		return toEntity(returnValue);
	}

	private Entity toEntity(Object returnValue) {
		Entity entityValue;
	    entityValue = (Entity) returnValue;
		return entityValue;
	}

	private Object[] createParameters(ArgumentList args) {
		Object[] objects = new Object [getNumberOfParameters()];
		for (int i = 0; i < objects.length; i++) {
			Entity nextArg = args.next();
		    objects[i] = nextArg;
		}
		return objects;
	}

	private int getNumberOfParameters() {
		return method.getParameterTypes().length;
	}
	
	private Object callMethodOnObject(Entity object, Object[] parameters) {
		try {
			return method.invoke(object, parameters);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println("Exposed Function " + getName());
			for(Object o : parameters) {
				System.out.print(o.toString() + " " + o.getClass().toString());
			}
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return SparseNull.theNull;
	}

	
}