(import "sparseSource/map.sp")

(defun double (x) (+ x x))

(assert (list) (map (list) double))
(assert (list 4) (map (list 2) double))
(assert (list 4 6) (map (list 2 3) double))
(assert (list 4 6 8 10) (map (list 2 3 4 5) double))
