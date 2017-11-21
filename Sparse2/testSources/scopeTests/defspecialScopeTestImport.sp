(import "testSources/scopeTests/defspecialScopeTest.sp")

(assert-throws-exception (special-add-1) "special-add-1 should throw exception, variable not bound")

(let ((a 1))
	(assert 2 (special-add-1) "special-add-1 should return 1 + a"))