(import "sparseSource/defmacro.sp")

(defun toTest () "global defun")
   
(defmacro testmacro ()
    (bind 43 a)
    (defun toTest () "defmacro defun"))
    
(testmacro)
(assert "defmacro defun" (toTest))
(assert 43 a)

(defspecial special ()
	(list '+ 2 3))

(defmacro macro ()
	(list '+ 2 3))

(assert '(+ 2 3) (special) "special form")
(assert 5 (macro) "macro")