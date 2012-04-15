(defun recurse (num)
   (if num (recurse (sub num 1))
           (print "******"))
   (print num))
   
(recurse 10)