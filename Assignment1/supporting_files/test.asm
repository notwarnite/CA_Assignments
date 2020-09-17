	.data
n:
	20
	.text
main:
	addi %x0, 65535, %x1
	load %x0, $n, %x2
	sub %x1, %x2, %x3
	addi %x0, 0, %x4
	addi %x0, 1, %x5
	store %x0, 0, %x1
	subi %x1, 1, %x1
	store %x5, 0, %x1
	subi %x1, 1, %x1
loop:
	beq %x1, %x3, exit
	addi %x5, 0, %x6
	add %x4, %x5, %x7
	store %x7, 0, %x1
	subi %x1, 1, %x1
	addi %x6, 0, %x4
	addi %x7, 0, %x5
	jmp loop
exit:
	end