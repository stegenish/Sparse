(defspecial defmacro (name params (rest body))
   (eval (concat (list 'defspecial name params) body)))

(export defmacro)