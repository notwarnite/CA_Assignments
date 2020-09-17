	.data
a:
	11
	.text
main:
	load %x0, $a, %x1
	divi %x1, 2, %x2
	addi %x0, 2, %x3
loop:
	beq %x3, %x2, isPrime
	div %x1, %x3, %x4
	beq %x31, 0, notPrime
	addi %x3, 1, %x3
	jmp loop
isPrime:
	addi %x0, 1, %x10
	end
notPrime:
	subi %x0, 1, %x10
	end