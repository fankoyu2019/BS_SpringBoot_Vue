import pandas as pd
import sys
import numpy as np
from sklearn import tree
import joblib

model = joblib.load('F://py//model.pkl')

def predict(PatientInfoArray):
    boolPredict = model.predict([PatientInfoArray])
    print(boolPredict)

if __name__ == '__main__':
    for i in range(1,len(sys.argv)):
        array = sys.argv[i].split(',')
        array = list(map(float,array))
        for j in range(1,len(array)+1):
            if j!=10:
                array[j-1]=int(array[j-1])
        predict(array)
    
    
