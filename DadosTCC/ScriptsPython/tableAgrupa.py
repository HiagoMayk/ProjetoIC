import fileinput
import numpy
import matplotlib.pyplot as plt
import string


lista = []
metricas = {"PMLL":[],"PMLH":[],"PMHL":[],"PMHH":[],"SL":[],"LM":[],"EA":[],"SAE":[],"ER":[],"RE":[],"TRE":[],"TF":[],"SAEF":[], "REF":[], "TREF":[]}

argX = open("ResultadosTCC/BLS/64/Table/vopd_table_xy.txt", 'r')

argY = open("ResultadosTCC/BLS/64/Table/vopd_table_xy_yx.txt", 'r')

for line in argY.readlines():
    #print line
    if line != "\n":
        l = line.split()
        metricas["PMLL"].append(int(l[0]))
            
        metricas["PMLH"].append(int(l[2]))
            
        metricas["PMHL"].append(int(l[4]))
            
        metricas["PMHH"].append(int(l[6]))
            
        metricas["SL"].append(int(l[8]))
            
        metricas["LM"].append(int(l[10]))

        metricas["EA"].append(int(l[12]))

        metricas["SAE"].append(int(l[14]))

        metricas["ER"].append(int(l[16]))

        metricas["RE"].append(int(l[18]))

        metricas["TRE"].append(float(l[20][:-1]))
            
        metricas["TF"].append(int(l[22][6:]))

        metricas["SAEF"].append(int(l[24]))

        metricas["REF"].append(int(l[26]))

        metricas["TREF"].append(float(l[28][:-1]))


lista = {"DLX":[], "DTX":[], "DLY":[], "DTY":[], "CLX":[], "CTX":[], "CLY":[], "CTY":[], "SLX":[], "STX":[], "SLY":[], "STY":[],"BLX":[], "BTX":[],"BLY":[], "BTY":[]}
count = 0
for line in fileinput.input():
    l = []
    t = []

    if line != "\n":
        if count < 6:	
            l = line.split()[2]
            t = line.split()[6]
            lista["DLX"].append(l)
            lista["DTX"].append(t)
            count += 1
        elif count < 12:
            l = line.split()[2]
            t = line.split()[6]
            lista["DLY"].append(l)
            lista["DTY"].append(t)
            count += 1
        elif count < 18:
            l = line.split()[2]
            t = line.split()[6]
            lista["CLX"].append(l)
            lista["CTX"].append(t)
            count += 1
        elif count < 24:
            l = line.split()[2]
            t = line.split()[6]
            lista["CLY"].append(l)
            lista["CTY"].append(t)
            count += 1
        elif count < 30:
            l = line.split()[2]
            t = line.split()[6]
            lista["SLX"].append(l)
            lista["STX"].append(t)
            count += 1
        elif count < 36:
            l = line.split()[2]
            t = line.split()[6]
            lista["SLY"].append(l)
            lista["STY"].append(t)
            count += 1
        elif count < 42:
            l = line.split()[2]
            #t.append(line.split()[-2])
            lista["BLX"].append(l)
            #lista["STX"].append(t)
            count += 1
        elif count < 48:
            l = line.split()[2]
            #t.append(line.split()[-2])
            lista["BTX"].append(l)
            #lista["STX"].append(t)
            count += 1
        elif count < 54:
            l = line.split()[2]
            #t.append(line.split()[-2])
            lista["BLY"].append(l)
            #lista["STX"].append(t)
            count += 1
        elif count < 60:
            l = line.split()[2]
            #t.append(line.split()[-2])
            lista["BTY"].append(l)
            #lista["STX"].append(t)
            count += 1

print lista

#{"DLX":[], "DTX":[], "DLY":[], "DTY":[], "CLX":[], "CTX":[], "CLY":[], "CTY":[], "SLX":[], "STX":[], "SLY":[], "STY":[],"BLX":[], "BTX":[],"BLY":[], "BTY":[]}
for i in range(0,6):
	print "%s \t & \t %s \t & \t %s \t & \t %s \t & \t %s \t & \t %s \t & \t %s \t & \t %s \\\\" %(lista["DLX"][i], lista["DLY"][i], lista["CLX"][i], lista["CLY"][i], lista["SLX"][i], lista["SLY"][i], lista["BLX"][i], lista["BLY"][i])

print
print

for i in range(0,6):
	print "%s \t & \t %s \t & \t %s \t & \t %s \t & \t %s \t & \t %s \t & \t %s \t & \t %s \\\\" %(lista["DTX"][i], lista["DTY"][i], lista["CTX"][i], lista["CTY"][i], lista["STX"][i], lista["STY"][i], lista["BTX"][i], lista["BTY"][i])


for i in range(0, 6):
    lista["DLX"][i] = int(lista["DLX"][i])
    lista["DTX"][i] = float(lista["DTX"][i][:-2])
    lista["DLY"][i] = int(lista["DLY"][i])
    lista["DTY"][i] = float(lista["DTY"][i][:-2])
    lista["CLX"][i] = int(lista["CLX"][i])
    lista["CTX"][i] = float(lista["CTX"][i][:-2])
    lista["CLY"][i] = int(lista["CLY"][i])
    lista["CTY"][i] = float(lista["CTY"][i][:-2])
    lista["SLX"][i] = int(lista["SLX"][i])
    lista["STX"][i] = float(lista["STX"][i][:-2])
    lista["SLY"][i] = int(lista["SLY"][i])
    lista["STY"][i] = float(lista["STY"][i][:-2])
    lista["BLX"][i] = float(lista["BLX"][i])
    lista["BTX"][i] = float(lista["BTX"][i])
    lista["BLY"][i] = float(lista["BLY"][i])
    lista["BTY"][i] = float(lista["BTY"][i])


print

minimo1 = numpy.min(metricas["PMLL"])
maximo1 = numpy.max(metricas["PMLL"])

#application = ["Integral de Romberg", "MPEG-4", "MWD", "Pipe", "Tree", "VOPD"]


N = 5 # esse parametro deve mudar de acordo com a aplicacao indo de 0 a 5
plt.title("VOPD")

plt.xlim(0, maximo1+10)
#plt.xlim(0, lista["SLX"][N]+10)
#plt.xlim(0, 1200)
plt.ylim(0, 100)
plt.xlabel("Latencia")
plt.ylabel("Taxa de Reuso")

plt.scatter(x = metricas["PMLL"], y = metricas["TREF"], alpha=0.5, color='blue')

plt.scatter(x = lista["DLY"][N], y = lista["DTY"][N], alpha=1.0, color='green')

plt.scatter(x = lista["CLY"][N], y = lista["CTY"][N], alpha=1.0, color='red')

plt.scatter(x = lista["SLY"][N], y = lista["STY"][N], alpha=1.0, color='black')
    
plt.show()

#plt.savefig("64/Table/" + ap + "_xy_yx.png")
