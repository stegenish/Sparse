(print "about to loop")
(bind 0 a)
(while (< a 5)
   (print "loop " a)
   (bind (add a 1) a))

(assert 5 a)