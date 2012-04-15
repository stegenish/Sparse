(bind  (if false
           123
           321)
    test1)

(assert 321 test1 "should have selected second expression")
    
(bind (if true
          123
          321)
    test2)
    
(assert 123 test2 "should have selected second expression")

(if (add -2 (add 1 1))
     (print "not zero")
     (print "printing this"))
     
(if (add 2 (add 1 2))
     (print "not zero")
     (print "printing this"))