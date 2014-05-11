(import "sparseSource/defmacro.sp")

(defmacro deftest (name args (rest body))
  (concat (list 'defun name args) (list (list 'print 'body)) body))

(deftest mytest ()
	(assert 1 1))

(mytest)