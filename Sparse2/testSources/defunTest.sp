
(defun adder (a b c)
    (add a b c))

(assert 6 (adder 1 2 3))

(defun longerFun (a b c)
  (add a b c)
  (print a)
  c
  (let ((d b)) d))
  
(assert 5 (longerFun 4 5 6))