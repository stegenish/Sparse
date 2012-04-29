(defun test1 (a b)
  (assert true (isbound a))
  (assert true (isbound b))
  (assert false (isbound c)))