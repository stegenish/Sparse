package sparser;

/*
 * A function scope is a scope allows access to all local and global variables.
 * 
 * To compare to Java:
 * 
 * public void method() {
 *     //Function scope starts here
 *     int a = 1;
 *     int b = 2;
 *     {
 *        //shadow scope start
 *        int a;
 *        a = b; //a is now 2
 *        //shadow scope end
 *     }
 *     //a is 1 here
 *     //Function scope ends here
 * }
 * 
 */
public class FunctionScopeSemantics implements ScopeSemantics {
	
	public Scope createNewScope(Scope scope) {
		return scope.createFunctionScope();
	}
}