#!/usr/bin/env python
# -*- coding: utf-8 -*-

import fileinput

# Recebe o arquivo com o grafo da aplicação no formato
# nome
# tamanho do problema
# ligações

def inputFile():

    flag = 0
    count = 0
    lista_a = []
    lista_b = []
    #la = ["PMLL", "PMLH", "PMHL", "PMHH", "SL", "LM", "EA", "SAE", "ER", "RE", "TRE", "TF", "SAEF", "REF", "TREF"]
    #lb = ["PMLL", "PMLH", "PMHL", "PMHH", "SL", "LM", "EA", "SAE", "ER", "RE", "TRE", "TF", "SAEF", "REF", "TREF"]
    #lista_a.append(la)
    #lista_b.append(lb)
    la = []
    lb = []

    arq_a = open("ResultadosTCC/Sequencial/64/Table/table_xy.txt", 'a')
    arq_b = open("ResultadosTCC/Sequencial/64/Table/table_xy_yx.txt", 'a')

    for line in fileinput.input():
        if flag == 0:
            if line[0] == "(":
                flag = 1

        if flag == 2:
            if line[0] == "(":

                flag = 3

        if flag == 1:
            if count < 15:
                #la.append(line.split()[-1])
                arq_a.write(line.split()[-1])
                arq_a.write("\t & \t")
                count+=1
            else:
                count = 0
                #lista_a.append(la)
                arq_a.write("\\\\ \n")
                #print la
                flag = 2

        if flag == 3:
            if count < 15:
               # lb.append(line.split()[-1])
                arq_b.write(line.split()[-1])
                arq_b.write("\t & \t")
                count+=1
            else:
                count = 0
                #lista_b.append(lb)
                arq_b.write("\\\\ \n")
                flag = 1

    arq_a.close()
    arq_b.close()



    #print lista_a
    #print ""
    #print lista_b




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
