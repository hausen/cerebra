ANTLRJAR:=antlr-3.5.1-complete-no-st3.jar
CLIJAR:=commons-cli-1.2.jar
PROGNAME:=Cerebra
VERSION:=0.3
BUILDDIR:=build
SRCS:=$(wildcard *.java)
OBJS:=$(SRCS:%.java=$(BUILDDIR)/%.class)
DIR:=$(shell pwd)
JARDIR:=$(DIR)/jars
CLASSPATH:=$(DIR):$(DIR)/parser:$(DIR)/build:$(JARDIR)/$(ANTLRJAR):$(JARDIR)/$(CLIJAR)
JAVA:=java
JAVAC:=javac
JC:=$(JAVAC) -g -classpath "$(CLASSPATH)"

all: $(BUILDDIR) parser/CerebraParser.java $(BUILDDIR)/Cerebra.class $(OBJS)

dist: all distrib/
	cd $(BUILDDIR) && jar xvf "$(JARDIR)/$(ANTLRJAR)" org/antlr/runtime
	cd $(BUILDDIR) && jar xvf "$(JARDIR)/$(CLIJAR)" org/apache
	rm -rf $(BUILDDIR)/META-INF
	mkdir $(BUILDDIR)/META-INF
	@echo Manifest-Version: 1.0 > $(BUILDDIR)/META-INF/MANIFEST.MF
	@echo Built-By: $(USER) >> $(BUILDDIR)/META-INF/MANIFEST.MF
	@echo Main-Class: Cerebra >> $(BUILDDIR)/META-INF/MANIFEST.MF
	jar cvmf $(BUILDDIR)/META-INF/MANIFEST.MF distrib/$(PROGNAME)-$(VERSION).jar -C $(BUILDDIR) .

run: all
	$(JAVA) -classpath "$(CLASSPATH)" Cerebra

distrib/:
	mkdir distrib

parser/CerebraParser.java: Cerebra.g
	java -jar "$(JARDIR)/$(ANTLRJAR)" Cerebra.g -o parser

$(BUILDDIR)/%.class: %.java
	$(JC) "$<" -d "$(BUILDDIR)"

$(BUILDDIR):
	mkdir $(BUILDDIR)

clean:
	rm -rf parser build
