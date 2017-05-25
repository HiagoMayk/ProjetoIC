#!/usr/bin/env python
# -*- coding: utf-8 -*-

import fileinput
import string
import numpy
import matplotlib.pyplot as plt

lista = []
metricas = {"PMLL":[],"PMLH":[],"PMHL":[],"PMHH":[],"SL":[],"LM":[],"EA":[],"SAE":[],"ER":[],"RE":[],"TRE":[],"TF":[],"SAEF":[], "REF":[], "TREF":[]}

for line in fileinput.input():
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

#custos = []
#iteracoes = []
#tempoFind = []

#for l in lista:
#		custos.append(int(l[0]))
#		iteracoes.append(int(l[1]))
#		tempoFind.append(float(l[2]))

print numpy.mean(metricas["PMLH"]), "&", numpy.std(metricas["PMLH"]), "&", numpy.mean(metricas["PMHL"]), "&", numpy.std(metricas["PMHL"]), "&", numpy.mean(metricas["PMHH"]), "&", numpy.std(metricas["PMHH"]), "&", numpy.mean(metricas["SL"]), "&", numpy.std(metricas["SL"]), "&", numpy.mean(metricas["LM"]), "&", numpy.std(metricas["LM"]), "&", numpy.mean(metricas["EA"]), "&", numpy.std(metricas["EA"]), "&", numpy.mean(metricas["SAE"]), "&", numpy.std(metricas["SAE"]), "&", numpy.mean(metricas["ER"]), "&", numpy.std(metricas["ER"]), "&", numpy.mean(metricas["RE"]), "&", numpy.std(metricas["RE"]), "&", numpy.mean(metricas["TRE"]), "&", numpy.std(metricas["TRE"]), "&", numpy.mean(metricas["TF"]), "&", numpy.std(metricas["TF"]), "&", numpy.mean(metricas["SAEF"]), "&", numpy.std(metricas["SAEF"]), "&", numpy.mean(metricas["REF"]), "&", numpy.std(metricas["REF"]), "\\\\"
print
print
minimo1 = numpy.min(metricas["PMLL"])
maximo1 = numpy.max(metricas["PMLL"])
print numpy.mean(metricas["PMLL"]), "&", numpy.std(metricas["PMLL"]), "&", minimo1, "&", metricas["PMLL"].count(minimo1), "&", maximo1, "&", metricas["PMLL"].count(maximo1), "\\\\"
print
print
minimo2 = numpy.min(metricas["TREF"])
maximo2 = numpy.max(metricas["TREF"])
print numpy.mean(metricas["TREF"]), "&", numpy.std(metricas["TREF"]), "&", minimo2, "&", metricas["TREF"].count(minimo2), "&", maximo2, "&", metricas["TREF"].count(maximo2), "\\\\"

print

ap = "vopd"

plt.title(ap)

plt.xlim(0, maximo1+50)
plt.ylim(0, 100)
plt.xlabel("Latencia")
plt.ylabel("Taxa de Reuso")

plt.scatter(x = metricas["PMLL"],
            y = metricas["TREF"], alpha=0.5)
#plt.show()
plt.savefig("64/Table/" + ap + "_xy_yx.png")
