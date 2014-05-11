(import "sparseSource/defmacro.sp")

(defspecial special ()
	(list '+ 2 3))

(defmacro macro ()
	(let ((a 2))
		(list '+ a 3)))

(assert '(+ 2 3) (special) "special form")
(assert 5 (eval (special)) "special form")
(assert 5 (macro) "macro")

