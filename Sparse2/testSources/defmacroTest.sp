(import "sparseSource/defmacro.sp")

(defun toTest () "global defun")

(defmacro testmacro ()
    (list 'defun 'toTest '() "defmacro defun"))
    
(testmacro)

(assert "defmacro defun" (toTest))
