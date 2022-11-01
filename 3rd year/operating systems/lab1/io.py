import subprocess
import re
import time
import matplotlib.pyplot as plt

values = []
times = []
time = 0

period = "0.1"
num = "300"

command = "/165788"

res = subprocess.check_output(["sudo", "iotop", "-P","-b", "-n", num, "-d", period])

for line in res.decode("utf-8").split("\n"):
  if re.search(command, line):
    sp_line = line.split(" ")
    for i in range(20):
      if '' in sp_line:
        sp_line.remove('')
    times.append(time)
    time += 0.1

    values.append(float(sp_line[9].replace(',', '.')))

fig, ax = plt.subplots()
ax.plot(times, values)
plt.show()
