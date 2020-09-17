	.data
a:
	10101
	.text
main:
	load %x0, $a, %x1
	load %x0, $a, %x2
loop:
	beq %x2, %x0, break
	divi %x2, 10, %x2
	muli %x3, 10, %x3
	add %x3, %x31, %x3
	jmp loop
break:
	beq %x1, %x3, isPalindrome
	subi %x0, 1, %x10
	end
isPalindrome:
	addi %x0, 1, %x10
	end