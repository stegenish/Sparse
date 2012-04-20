(bind a 3)
(print a)
(set a 4)
(print a)
(print (global-value a))

(defun test (a)
   (print a)
   (print (global-value a)))
   
(test 42)
(print a)
(print (global-value a))