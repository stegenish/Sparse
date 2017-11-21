;(defspecial defmacro (name params (rest body))
;   (eval (concat (list 'defspecial name params) (list 'eval body))))


(defspecial defmacro (name params (rest body))
  (let ((new-body (list))
	(remaining body))
    (while (not (is-nil remaining))
      (if (not (is-nil (rest remaining)))
	  (append new-body (first remaining))
	(append new-body (list 'eval (first remaining))))
      (set remaining (rest remaining)))
    (eval (concat (list 'defspecial name params) new-body))))
     
(export defmacro)
