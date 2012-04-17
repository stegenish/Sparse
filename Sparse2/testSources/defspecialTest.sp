(defspecial special () 2)

(bind (special) specialValue)
(assert 2 specialValue "special should return a value")

(defspecial special (a) a)
(bind (special 2) specialValue)
(assert 2 specialValue "special should return a value")

(bind (special (1 2 3)) specialValue)
(assert (quote (1 2 3)) specialValue "special should return a value")

(defspecial special (a) b)
(defun testSpecial (b) 
    (special 3))

(bind (testSpecial 2) result)
(assert 2 result "Specials can access parent scope")

(defspecial with-rest (a (&rest more))
   more)
   
(assert (quote (1 2 3)) (with-rest "" 1 2 3))

(print "strangeness")
(defspecial failing (p1 p2)
   (let ((p1 (eval p1))
         (p2 (eval p2)))
    (print (add p1 p2))))

(defun test1 () 1)
(defun test2 () 2)

(failing (test1) (test2))

(failing 1 2)
