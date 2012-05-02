(defun map (lst fun)
  (let ((this-element (first lst))
	(remaining (rest lst)))
    (if (equal this-element null)
	(list)
      (concat (list (fun this-element))
	      (map remaining fun)))))

(export map)  
