;(import "sparseSource/defmacro.sp")

(defmacro deftest (name args (rest body))
  `(defun ,name ,args (print ,body) ,@body))

(deftest mytest ()
	(assert 1 1))

(mytest)