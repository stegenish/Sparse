(assert-throws-exception (set a 3))

(let ((a 1))
  (assert a 1)
  (set a 2)
  (assert a 2))
  
(assert-throws-exception (set a 3))