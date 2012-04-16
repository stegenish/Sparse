(bind 4 a)
(bind 5 b)
(bind 7 c)

(assert 4 a)
(assert 5 b)

(let 
   ((a 1)
    (b (add 1 2)))
   (assert 1 a "a should shadow a in global scope in let")
   (print "a:" a)
   (assert 3 b "b should shadow b in global scope in let")
   (print "b:" b)
   (assert 7 c "c should access global binding in let")
   (print "c:" c))