package sparser;

/*
 * A shadow scope is a scope that contains all the bindings that are 
 * in scope when the shadow scope is created. When a symbol is rebound
 * in the shadow scope it does not affect the original binding.
 * 
 * To compare to Java
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
public class ShadowScopeSemantics implements ScopeSemantics{
	
	public Scope createNewScope(Scope scope) {
		return scope.createShadowScope();
	}
}