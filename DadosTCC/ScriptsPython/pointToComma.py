import fileinput

for line in fileinput.input():
    line = line.replace('.', ',')
    print line