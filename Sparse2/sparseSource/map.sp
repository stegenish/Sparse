(defun map (lst fun)
  (let ((this-element (first lst))
	    (remaining (rest lst)))
    (if (equal null this-element)
	  (list)
      (concat (list (fun this-element))
	          (map remaining fun)))))
  
