run: compiled/ioopm
	@java -cp compiled/ ioopm.calculator.TestDriver

test: compiled/ioopm
	@java -cp compiled/junit/*:compiled/ org.junit.runner.JUnitCore ioopm.calculator.TestJunit

system-test: compiled/ioopm
	@java -cp compiled/ ioopm.calculator.TestDriver < testFiles/input.txt > testFiles/output.txt
	@cat testFiles/output.txt | grep -v Welcome | grep -v Of | cut -d : -f 2 | cut -c 2- > testFiles/tmp.txt
	@! diff testFiles/tmp.txt testFiles/expectedOutput.txt || echo "All tests passed!"

compile:
	@javac -cp compiled/junit/*:. -d compiled/ *.java */*.java

compiled/ioopm:
	@make compile

clean:
	@rm -rf compiled/ioopm
