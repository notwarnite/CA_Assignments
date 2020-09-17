	.data
a:
	13
	.text
main:
	load %x0, $a, %x1
	addi %x0, 1, %x3
	divi %x1, 2, %x2
	beq %x0, %x31, iseven
	addi %x0, 1, %x10
	end
iseven:
	subi %x0, 1, %x10
	end