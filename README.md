# ChairBakesInterpreter
An interpreter for the Chair Bakes language, which is almost the same as Hunter Allen's Bench Cookie, a functional language, but it is slightly different.

To build on the command line, use this:
```
./buildCBI.sh
```

This will compile a JAR in the project's root directory, CBITests-1.0.jar.
Then you should be able to run the interpreter on Chair Bakes source files that you create with this command:
```
java -jar CBITests-1.0.jar myProgram.cb
```

Please remember, all Chair Bakes files must have a main function.

Examples:
```
func main [
	print <-- "Hello world";
]
```
