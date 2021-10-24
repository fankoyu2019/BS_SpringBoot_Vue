import pandas as pd
import sys
import numpy as np


data = pd.read_csv("F://py//heart_cleveland_upload.csv")
features = data[data.columns.difference(["condition"])]
target = data[['condition']]

from sklearn import tree
model = tree.DecisionTreeClassifier()
model = model.fit(features,target)

import joblib
joblib.dump(model,'F://py//model.pkl')

#td=pd.read_csv("F://py//test.csv")
#X=td.drop(['condition'],axis=1)
#name_list=[]
#to_list=model.predict(X)
#for i in to_list:
#    name_list.append(i)
#td['condition']=name_list
#td.to_csv("test.csv",encoding='utf-8')


#predict1 = model.predict([[69,0,0,140,239,0,0,151,0,1.8,0,2,0]])




#import graphviz
#dot_data=tree.export_graphviz(model,out_file=None)
#graph = graphviz.Source(dot_data)
#graph.render("heart_decision_tree")



