(import "sparseSource/defmacro.sp")

(defun toTest () "global defun")
   
(defmacro testmacro ()
    (bind 43 a)
    (defun toTest () "defmacro defun"))
    
(testmacro)
(assert "defmacro defun" (toTest))
(assert 43 a)
