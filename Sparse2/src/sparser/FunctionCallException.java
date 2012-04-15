/*
 * FunctionCallException.java
 *
 * Created on 14 August 2004, 09:11
 */

package sparser;

/**
 *
 * @author  Administrator
 */
public class FunctionCallException extends RuntimeException
{

	private static final long serialVersionUID = -113071347601870163L;

	public FunctionCallException(String msg) {
		super(msg);
	}

	public FunctionCallException() {
		// Default constructor
	}

}
