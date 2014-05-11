(let ((a 1))
   (defun enclosing () (print (set a (+ a 1)))))
   
(assert (enclosing) 2 "Should add 1 to captured variable")
(assert (enclosing) 3 "Should add 1 to captured variable")