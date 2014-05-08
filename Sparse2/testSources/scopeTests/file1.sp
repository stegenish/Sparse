(defun test1 (a b)
  (assert true (isbound a) "a should be bound")
  (assert true (isbound b) "b should be bound")
  (assert false (isbound c) "c should not be bound"))
  
(export test1)