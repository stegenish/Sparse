(defspecial defun1 (name param (&rest body))
     (eval (concat (list 'defspecial name param)
               (list 
                 (concat 
                   (list 'let (list 
                     (list (first param) (list 'eval (first param)))))
                   body)))))
   
(defun1 fun (param1) 
   (print param1))
   
(defspecial fun2 (param1) 
   (print param1))

(fun (list 1 2 3 ))
(fun2 (list 1 2 3 ))