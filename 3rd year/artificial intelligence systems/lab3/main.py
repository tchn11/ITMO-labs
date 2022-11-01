import matplotlib.pyplot as plt
from dataset import *
from tree import *

keys, attributes = readDataset("agaricus-lepiota.data")

tree = createDisTree(keys, attributes)

accurancy, precision, recall, ROC_TP, ROC_FP, PR_PR, PR_RC = calculate_metrics(tree, keys, attributes)

print("accurance = ", accurancy)
print("precision = ", precision)
print("recall = ", recall)

lw = 2

plt.plot(ROC_FP, ROC_TP, lw=lw, label='ROC curve ')
plt.plot([0, 1], [0, 1])
plt.xlim([0.0, 1.0])
plt.ylim([0.0, 1.05])
plt.xlabel('False Positive Rate')
plt.ylabel('True Positive Rate')
plt.title('ROC curve')
plt.show()

plt.plot(PR_RC, PR_PR, lw=lw, label='PR curve')
plt.xlim([0.0, 1.0])
plt.ylim([0.0, 1.05])
plt.xlabel('Recall')
plt.ylabel('Precision')
plt.title('PR curve')
plt.show()
