#!/usr/bin/env python
# coding: utf-8

# In[1]:


import pandas as pd
import numpy as np

reviews = pd.read_csv('data/reviews.csv')
print(len(reviews))
reviews.head()


# In[2]:


reviews = reviews.loc[reviews['HelpfulnessDenominator'] > 3]
print(len(reviews))


# In[3]:


texts = reviews['Text']
Y = reviews['HelpfulnessNumerator'] / reviews['HelpfulnessDenominator']
Y = Y.map(lambda x: 0 if x < 0.33 else (1 if x < 0.67 else 2))
print(len(Y.loc[Y == 0]))
print(len(Y.loc[Y == 1]))
print(len(Y.loc[Y == 2]))


# In[13]:


from sklearn.feature_extraction.text import CountVectorizer

vectorizer = CountVectorizer(stop_words='english', min_df=50)
X = vectorizer.fit_transform(texts)
print(X.shape)


# In[14]:


from sklearn.model_selection import train_test_split

X_train, X_test, y_train, y_test = train_test_split(X, Y, test_size=0.2, random_state=0)
print(X_train.shape)
print(X_test.shape)


# In[15]:


from sklearn.svm import SVC

model = SVC(gamma='scale', max_iter=100000, decision_function_shape='ovo', random_state=0, verbose=True)
model.fit(X_train, y_train)


# In[16]:


print(model.score(X_train, y_train))
print(model.score(X_test, y_test))


# In[17]:


from sklearn.metrics import precision_recall_fscore_support

precision, recall, f1, support = precision_recall_fscore_support(y_test, model.predict(X_test), average=None, labels=[0, 1, 2])
print('Results in order by class: [0, 1, 2]')
print('Precision:', precision)
print('Recall:', recall)
print('F1:', f1)
print('# Occurrences:', support)


# In[ ]:




