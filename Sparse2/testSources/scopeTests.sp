
(bind 3 a)
(defun fun (a)
   (assert 4 a "checking local binding"))

(assert 3 a "checking global binding")
(fun 4)
(assert 3 a "rechecking global binding ")

(print "Checked global and parameter")

(defun level2 (a)
	(assert 43 a "Checking at level 2")
	(print "At level 2" a))

(defun level1 (a)
	(level2 43)
	(assert 34 a "Checking at level 1")
	(print "At level 1" a))

(print "At global" a)
(level1 34)

(assert 3 a "rechecking global binding again")

(defun level2_2 ()
    (assert 3 a "Checking a at level 2, should be global binding")
    (print "At level 2 2" a))

(defun level2_1 (a)
    (level2_2)
    (assert 34 a "Checking at level 1")
    (print "At level 2 1" a))
    
(level2_1 34)