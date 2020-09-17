	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	addi %x0, 1, %x3
	load %x0, $n, %x8
loopMain:
	add %x0, %x0, %x4
	addi %x0, 1, %x5
loopSub:
	load %x4, $a, %x6
	load %x5, $a, %x7
	bgt %x6, %x7, noSwap
	store %x7, $a, %x4
	store %x6, $a, %x5
noSwap:
	addi %x4, 1, %x4
	addi %x5, 1, %x5
	beq %x5, %x8, checkSorted
	jmp loopSub
checkSorted:
	subi %x8, 1, %x8
	beq %x8, %x3, exit
	jmp loopMain
exit:
	end