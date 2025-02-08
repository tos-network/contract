MAKEFLAGS = -s

# Need to set JAVA_HOME to the path of the OpenJDK 8 installation

# Basic variables
JAVA_HOME ?= /Library/Java/JavaVirtualMachines/openjdk-8.jdk/Contents/Home
version := $(shell grep version gradle.properties | cut -d'=' -f2)

# Java tools
javac = "$(JAVA_HOME)/bin/javac" -encoding UTF-8
jar = "$(JAVA_HOME)/bin/jar"

# Directories
build = build
classpath-src = src
classpath-build = $(build)/classpath

# Classpath settings
boot-classpath = $(JAVA_HOME)/jre/lib/rt.jar

# Convert paths for Java compilation
java-classes = $(foreach x,$(1),$(patsubst $(2)/%.java,$(3)/%.class,$(x)))

# Find all Java source files
classpath-sources := $(shell find $(classpath-src) -name '*.java')
classpath-classes = $(call java-classes,$(classpath-sources),$(classpath-src),$(classpath-build))
classpath-dep = $(classpath-build).dep

# Default target
.PHONY: build
build: $(classpath-dep) $(build)/tl.jar

# Compile Java sources
$(classpath-dep): $(classpath-sources)
	@echo "compiling java sources "
	@mkdir -p $(classpath-build)
	$(javac) -source 1.8 -target 1.8 \
		-d $(classpath-build) \
		-bootclasspath $(boot-classpath) \
		$(classpath-sources)
	@touch $(@)

# Create JAR file
$(build)/tl.jar: $(classpath-dep)
	@echo "creating $(@)"
	@mkdir -p $(dir $(@))
	(cd $(classpath-build) && \
	 $(jar) c0f "../tl.jar" .)

# Clean target
.PHONY: clean
clean:
	@echo "removing build directory"
	rm -rf $(build)
