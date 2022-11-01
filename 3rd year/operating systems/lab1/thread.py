import subprocess
import time
import matplotlib.pyplot as plt

threads_num = 25
measure_time = 30
pid = "2246"

values = []
times = []

start_time = time.time()

while time.time() - start_time < measure_time:
  res = subprocess.check_output(["top", "-p", pid, "-H", "-o", "PID", "-b", "-n", "1"]).decode("utf-8").split("\n")
 
  summ = 0
  for k in range(threads_num):
    res_tmp = res[7 + k].split(" ")
    for i in range(20):
      if '' in res_tmp:
        res_tmp.remove('')

    if res_tmp[7] == "R":
      summ += 1
  times.append(time.time() - start_time)
  values.append(summ)

fig, ax = plt.subplots()
ax.step(times, values)
plt.show()

