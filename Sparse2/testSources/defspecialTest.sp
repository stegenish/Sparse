(print "strangeness")
(defspecial failing (p1 p2)
   (let ((p1 (eval p1))
         (p2 (eval p2)))
    (print p1 p2)))

(defun test1 () 1)
(defun test2 () 2)

(failing (test1) (test2))
