#!/usr/bin/env python
# coding: utf-8

# In[4]:


import pandas as pd
import numpy as np

reviews = pd.read_csv('data/reviews.csv')
print(len(reviews))
reviews.head()


# In[5]:


reviews = reviews.loc[reviews['HelpfulnessDenominator'] > 3]
print(len(reviews))


# In[6]:


reviews = reviews.dropna(subset=['Summary'])
print(len(reviews))


# In[7]:


texts = reviews['Text']
summaries = reviews['Summary']
Y = reviews['HelpfulnessNumerator'] / reviews['HelpfulnessDenominator']
Y = Y.map(lambda x: 0 if x < 0.33 else (1 if x < 0.67 else 2))
print(len(Y.loc[Y == 0]))
print(len(Y.loc[Y == 1]))
print(len(Y.loc[Y == 2]))


# In[8]:


from sklearn.feature_extraction.text import CountVectorizer

textVec = CountVectorizer(stop_words='english', min_df=8)
summaryVec = CountVectorizer(stop_words='english', min_df=5)
X_texts = textVec.fit_transform(texts)
X_summaries = summaryVec.fit_transform(summaries)
print(X_texts.shape)
print(X_summaries.shape)


# In[9]:


from scipy.sparse import hstack
X = hstack([X_texts, X_summaries])
print(X.shape)


# In[10]:


from sklearn.model_selection import train_test_split

X_train, X_test, y_train, y_test = train_test_split(X, Y, test_size=0.2, random_state=0)
print(X_train.shape)
print(X_test.shape)


# In[11]:


from sklearn.ensemble import RandomForestClassifier

model = RandomForestClassifier(n_estimators=100, max_depth=400, random_state=0, n_jobs=-1)
model.fit(X_train, y_train)


# In[9]:


print(model.score(X_train, y_train))
print(model.score(X_test, y_test))


# In[12]:


from sklearn.metrics import precision_recall_fscore_support

precision, recall, f1, support = precision_recall_fscore_support(y_test, model.predict(X_test), average=None, labels=[0, 1, 2])
print('Results in order by class: [0, 1, 2]')
print('Precision:', precision)
print('Recall:', recall)
print('F1:', f1)
print('# Occurrences:', support)


# In[ ]:




