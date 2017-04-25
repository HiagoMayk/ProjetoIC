#!/usr/bin/env python
# -*- coding: utf-8 -*-

import fileinput

# Recebe o arquivo com o grafo da aplicação no formato
# nome
# tamanho do problema
# ligações

def inputFile():

    lista = []
    la = ["PMLL", "PMLH", "PMHL", "PMHH", "SL", "LM", "EA", "SAE", "ER", "RE", "TRE", "TF", "SAEF", "REF", "TREF"]
    lista.append(la)
    for line in fileinput.input():
        if line[0] == "(":
            print line.split()[-1]


def printInFile(distGraph):
    arq = open("Codigos/InstanciasQAP64/" + name + ".in", 'w')

    arq.write("0")
    arq.write("\n")
    arq.write("\n")

    nodes = distGraph.keys()
    arq.write(str(len(nodes)))
    arq.write("\n")
    arq.write("\n")

    arq2 = open("Distance_matrix/mesh" + str(int(sqrt(len(nodes)))) + "x" + str(int(sqrt(len(nodes)))) + "_distance_matrix.in", 'r')
    texto = arq2.read()

    for i in nodes:
        for j in nodes:
            arq.write("%s\t"%(distGraph[i][j]))

        arq.write("\n")

    arq.write("\n")
    arq.write(texto)

    arq.close()
    arq2.close()


if __name__ == '__main__':

    inputFile()
