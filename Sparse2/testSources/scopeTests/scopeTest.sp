(import "testSources/scopeTests/file1.sp")

(bind 3 c)
(bind 4 a)
(test1 1 2)
(test-special 1 2)
(assert 4 a)