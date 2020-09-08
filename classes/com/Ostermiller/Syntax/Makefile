JAVAC=javac
JAVA=java
JAVADOC=javadoc
JLEX=$(JAVA) JFlex.Main
JCUP=$(JAVA) java_cup.Main

all: Lexer/JavaLexer.java \
	Lexer/CLexer.java \
	Lexer/HTMLLexer.java\
	Lexer/HTMLLexer1.java\
	Lexer/PlainLexer.java\
	Lexer/PropertiesLexer.java\
	Lexer/JavaScriptLexer.java
	$(JAVAC) Lexer/*.java
	$(JAVAC) *.java

Lexer/JavaLexer.java: Lexer/JavaLexer.lex
	$(JLEX) Lexer/JavaLexer.lex

Lexer/CLexer.java:Lexer/CLexer.lex
	$(JLEX) Lexer/CLexer.lex

Lexer/HTMLLexer.java: Lexer/HTMLLexer.lex
	$(JLEX) Lexer/HTMLLexer.lex

Lexer/HTMLLexer1.java: Lexer/HTMLLexer1.lex
	$(JLEX) Lexer/HTMLLexer1.lex
        
Lexer/PlainLexer.java: Lexer/PlainLexer.lex
	$(JLEX) Lexer/PlainLexer.lex

Lexer/PropertiesLexer.java: Lexer/PropertiesLexer.lex
	$(JLEX) Lexer/PropertiesLexer.lex

Lexer/JavaScriptLexer.java: Lexer/JavaScriptLexer.lex
	$(JLEX) Lexer/JavaScriptLexer.lex

clean:
	rm -f *.class *.jar *~ temp*
	rm -f Lexer/*.class Lexer/*~ Lexer/temp*
	rm -f Lexer/sym.java
	rm -f Lexer/JavaLexer.java
	rm -f Lexer/CLexer.java
	rm -f Lexer/HTMLLexer.java
	rm -f Lexer/HTMLLexer1.java
	rm -f Lexer/PlainLexer.java
	rm -f Lexer/PropertiesLexer.java
	rm -f Lexer/JavaScriptLexer.java
	rm -rf docs/ javadoc/
	rm -rf com/ gnu/

build:
	rm -f *.jar *~ temp*
	rm -f Lexer/*~ Lexer/temp*
	rm -rf com/ gnu/
	mkdir com
	mkdir com/Ostermiller
	mkdir com/Ostermiller/Syntax
	cp *.* Makefile com/Ostermiller/Syntax/
	mkdir com/Ostermiller/Syntax/Lexer
	cp Lexer/*.* com/Ostermiller/Syntax/Lexer/
	mkdir com/Ostermiller/Syntax/doc
	cp doc/*.* com/Ostermiller/Syntax/doc/        
	mkdir com/Ostermiller/util
	cp ../util/StringHelper.* com/Ostermiller/util/
	cp ../util/CircularCharBuffer.* com/Ostermiller/util/  
	cp ../util/BufferOverflowException.* com/Ostermiller/util/      
	mkdir com/Ostermiller/bte
	cp ../bte/CompileException.* com/Ostermiller/bte/
	mkdir gnu
	mkdir gnu/getopt		
	cp ../../../gnu/getopt/*.* gnu/getopt
	jar cmfv syntax.mf syntax.jar com/ gnu/
	rm -rf com/ gnu/

docs:
	rm -rf javadoc
	mkdir javadoc
	$(JAVADOC) -link http://java.sun.com/j2se/1.3/docs/api/ -d javadoc/ com.Ostermiller.Syntax com.Ostermiller.Syntax.Lexer
        
test:
	$(JAVA) com.Ostermiller.Syntax.ToHTML -f -t http://ostermiller.org/syntax/simple.bte -T "C Syntax Highlighting Test" -l com.Ostermiller.Syntax.Lexer.CLexer -i whitespace -o temp.html doc/TestCases.c 
	diff temp.html doc/TestCases.c.html 
	$(JAVA) com.Ostermiller.Syntax.ToHTML -f -t http://ostermiller.org/syntax/simple.bte -T "Simple HTML Syntax Highlighting Test" -l com.Ostermiller.Syntax.Lexer.HTMLLexer -i whitespace -o temp.html doc/TestCases.html
	diff temp.html doc/TestCases.html.simple.html 
	$(JAVA) com.Ostermiller.Syntax.ToHTML -f -t http://ostermiller.org/syntax/simple.bte -T "Complex HTML Syntax Highlighting Test" -l com.Ostermiller.Syntax.Lexer.HTMLLexer1 -i whitespace -o temp.html doc/TestCases.html
	diff temp.html doc/TestCases.html.complex.html 
	$(JAVA) com.Ostermiller.Syntax.ToHTML -f -t http://ostermiller.org/syntax/simple.bte -T "Java Syntax Highlighting Test" -l com.Ostermiller.Syntax.Lexer.JavaLexer -i whitespace -o temp.html doc/TestCases.java
	diff temp.html doc/TestCases.java.html 	
	$(JAVA) com.Ostermiller.Syntax.ToHTML -f -t http://ostermiller.org/syntax/simple.bte -T "Java Properties Syntax Highlighting Test" -l com.Ostermiller.Syntax.Lexer.PropertiesLexer -i whitespace -o temp.html doc/TestCases.properties
	diff temp.html doc/TestCases.properties.html 	
	rm temp.html syntax.css
        
	
