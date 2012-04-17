(bind 4 a)
(bind 5 b)
(bind 7 c)

(assert 4 a)
(assert 5 b)

(defun fun1 () 3)

(let 
   ((a 1)
    (b (add a 2))
    (z (fun1)))
   (assert 1 a "a should shadow a in global scope in let")
   (print "a:" a)
   (assert 6 b "b should shadow b in global scope in let, a comes from global scope")
   (print "b:" b)
   (assert 7 c "c should access global binding in let")
   (print "c:" c)
   (assert 3 z "z should access global binding in let"))
   
(defun letTest (d)
   (let ((localInFunction 42)) 
     localInFunction))
(assert 42 (letTest 5))

(defun letTest (d)
   (let ((localInFunction d)) 
     localInFunction))
(assert 5 (letTest 5))

(defun letTest (d)
   (let ((localInFunction 55)) 
     d))
(assert 5 (letTest 5))

(defun letTest (d)
   (let ((d 55)) 
     d))
(assert 55 (letTest 5))

(defun letTest (d)
   (let ((d 55)) 
     d)
     d)
(assert 5 (letTest 5))

(defun letTest (d)
   (let ((d 55)
         (f d)) 
     f))
(assert 5 (letTest 5))