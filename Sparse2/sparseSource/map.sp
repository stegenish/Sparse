(defun maptest (lst)
   (print (first lst))
   (if (equal (rest lst) null)
      null
      (maptest (rest lst))))

(defun map (lst fun)
   (print lst)
   (if (equal (first lst) null)
       null
     (concat (list (fun (first lst))) (map (rest lst) fun))))