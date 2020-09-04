
import pandas as pd
import numpy as np
import numpy as np
import scipy.interpolate as interp
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

print('Reading DATA from given file')
result =pd.read_csv('ca_assignment_0/output.csv')

X = result['Width'].values.tolist()
Y = result['P-value'].values.tolist()
Z = result['Average_Time_1000_runs'].values.tolist()

print('Creating Mesh-Grid for X, Y and Z values')
plotx,ploty = np.meshgrid(
					np.linspace(np.min(X),np.max(X),20),
                    np.linspace(np.min(Y),np.max(Y),20)
                    	)
plotz = interp.griddata((X,Y),Z,(plotx,ploty),method='linear')

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')
ax.plot_surface(plotx,ploty,plotz,cstride=1,rstride=1,cmap='viridis')

print('Putting Out Labels to the Graph Axes')
ax.set_xlabel("Border Width")
ax.set_ylabel("Sensor P-value")
ax.set_zlabel("Average Time Taken (1000 Experiments)")

ax.set_title('Average Time Taken by INFILTRATOR vs Sensor P-value and Border Width')

plt.show()