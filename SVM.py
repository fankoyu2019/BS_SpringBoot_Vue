import pandas as pd
import sys
import numpy as np


data = pd.read_csv("F://py//heart_cleveland_upload.csv")
features = data[data.columns.difference(["condition"])]
target = data[['condition']]

from sklearn import svm
model = svm.SVC()
model.fit(features,target)

td=pd.read_csv("F://py//testSVM.csv")
X=td.drop(['condition'],axis=1)
name_list=[]
to_list=model.predict(X)
for i in to_list:
    name_list.append(i)
td['condition']=name_list
td.to_csv("testSVM.csv",encoding='utf-8')
