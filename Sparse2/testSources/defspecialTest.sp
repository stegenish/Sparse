(print "strangeness")
(defspecial failing (p1 p2)
   (let ((p1 (eval (eval p1)))
         (p2 p2))
    (print p1 p2)))

(defun test1 () 1)
(defun test2 () 2)

(failing test1 test2)
