import subprocess
import re
import time
import matplotlib.pyplot as plt

values = []
times = []
time = 0

timeout = "30"

res = ""

try:
  tmp = subprocess.check_output(["timeout", timeout, "bmon", "-o", "format"])
except subprocess.CalledProcessError as grepexc:                                                                                                   
  res = grepexc.output

prev_value = 0

time_add = int(timeout) / (len(res.decode("utf-8").split("\n"))/4)

for line in res.decode("utf-8").split("\n"):
  if re.search("lo", line):
    sp_line = line.split(" ")
    
    for i in range(20):
      if '' in sp_line:
        sp_line.remove('')
        
    if prev_value == 0:
    	prev_value = int(sp_line[1]) + int(sp_line[3])

    times.append(time)
    time += time_add

    values.append((int(sp_line[1]) + int(sp_line[3])) - prev_value)
    prev_value = int(sp_line[1]) + int(sp_line[3])

fig, ax = plt.subplots()
ax.plot(times, values)
plt.show()
