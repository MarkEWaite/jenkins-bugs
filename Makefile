program: program.c
	cc -o program program.c

build:  program

check:  build
	./program | grep Hello.World

package: build
	tar cvzf build.tar.gz program

deploy: package
	(cd /tmp && tar xzvf - ) < build.tar.gz
	(cd /tmp && ./program)
