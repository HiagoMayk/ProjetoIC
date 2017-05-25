import fileinput

medias = []
desvios = []

for line in fileinput.input():
      flag = True
      m = []
      d = []
      for t in line.split():
            if t != "&" and t != "\\\\":
                  if flag:
                        #print "%.4f \t &" %float(t),
                        m.append(t)
                        flag = not flag
                  else:
                        d.append(t)
                        flag = not flag

      if line != "\n":
            medias.append(m)
            desvios.append(d)

for m in medias:
      for i in m:
            print "%.2f &" %float(i),

      print "\\\\"


print
print

for d in desvios:
      for i in d:
            print "%.2f &" %float(i),

      print "\\\\"

