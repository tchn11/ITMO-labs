import subprocess
import time
import matplotlib.pyplot as plt

measure_time = 10
PID = "2246"

values = []
times = []

start_time = time.time()

while time.time() - start_time < measure_time:
  res = subprocess.check_output(["top", "-b", "-p", PID, "-n", "1"]).decode("utf-8").split("\n")[7].split(" ")
  for i in range(50):
    if '' in res:
      res.remove('')
  values.append(float(res[8].replace(',', '.')))
  times.append(time.time() - start_time)

fig, ax = plt.subplots()
ax.plot(times, values)
plt.show()
