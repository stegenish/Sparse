(assert null (first (list)))

(import "sparseSource/map.sp")

(defun double (x) (add x x))

(assert (list 2 4) (map (list 1 2) double))

