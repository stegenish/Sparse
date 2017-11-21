(defmacro macro1 ()
  '(+ a 1))
  
(assert 3 (let ((a 2)) (macro1)) "return value of macro should execute in callers scope")