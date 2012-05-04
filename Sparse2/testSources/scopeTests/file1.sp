(defun test1 (a b)
  (assert true (isbound a))
  (assert true (isbound b))
  (assert false (isbound c)))

(defspecial test-special (a b)
  (assert true (isbound a))
  (assert true (isbound b))
  (assert true (isbound c)))

(export test1)
(export test-special)