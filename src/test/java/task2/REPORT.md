# Task 2 — report

## Task 2-1 (I/O)

`GuessNumberIOTest` redirects standard input/output and drives `guessingNumberGame(Random)`.

`GuessNumberInvoker` calls the real method via reflection: the course JAR puts `GuessNumber` in the **default package**, and Java does not let `package task2` import default-package types. Without a small bridge (or moving those tests to the default package), the project would not compile.

## Task 2-2 (randomness)

`GuessNumberRandomnessTest` runs **30,000** games with **one** `Random` seeded with **0**, records return values, and checks every count for 1..100 is within the assignment’s ±50% spread (`2 * max <= 3 * min + 1` on bucket counts).
