# symbolicCalculator
####Commands
+ **vars:** Displays all assigned variables.
+ **clear:** Clears all assigned variables.
+ **quit:** Quits the app.

###Mathematical expressions
Build mathematical expressions as you would normally do in a calculator. Assignments are done to variables, with the assigned value on the left side and the variable on the right side. Predefined mathematical functions are *sin*,*cos*,*exp* and *log*. Predefined mathematical variables are *pi*, *e* and *L*.
###Scopes
Scopes are similar to functions calls in the sense that all assignments made inside a scope only affect current said scope(and internal scopes). The syntax for scope are **{**expression**}**.
###Conditional Logic
Write simple if/else statements through syntax **if** *variable/number* **operator** *variable/number* *scope* **else** *scope*. Example:

```
if 6 < 4 {5} else {2}

if 6 == 4 {if 4 < 6 {5} else {2}} else {5}
```
###Functions
Declare functions through syntax **function** *name*(inputarg1,inputarg2,...,inputargn). Afterwards write expressions as one usually would and complete the function through keyword **end**. Example:

```
function example(x,y)
x + 10 = x 
y/x = y
if y == x {1} else {2}
end

```
Note that each expression is written in sequence after the function declaration.







