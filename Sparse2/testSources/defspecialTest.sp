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