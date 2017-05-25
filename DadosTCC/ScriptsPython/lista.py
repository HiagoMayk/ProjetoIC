import fileinput

lista = []

for line in fileinput.input():
	l = []
	l.append(line.split()[0])
	l.append(line.split()[10])
	l.append(line.split()[-3])
	lista.append(l)

for i in lista:
	for j in i:
		print j + "\t & \t",

	print "\\\\"
