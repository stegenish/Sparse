
(defspecial thisIsAFunction (name params (&rest body))
     (concat (list (quote defspecial) name params) body))
   
(print (thisIsAFunction fun (param1 param2) (add a) a param2))

(defspecial test (p1 p2)
   (let ((p1 (eval p1))
         (p2 (eval p2)))
       ))