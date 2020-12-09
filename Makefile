
default:
	@mvn compile
compile:
	@mvn compile
package:
	@mvn package
clean:
	@mvn clean
test:
	@mvn test

.PHONY: compile package clean test
