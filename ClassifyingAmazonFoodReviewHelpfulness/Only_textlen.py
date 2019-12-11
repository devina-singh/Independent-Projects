#!/usr/bin/env python
# coding: utf-8

# In[2]:


import pandas as pd
import numpy as np

reviews = pd.read_csv('data/reviews.csv')
print(len(reviews))
reviews.head()


# In[3]:


reviews = reviews.loc[reviews['HelpfulnessDenominator'] > 3]
print(len(reviews))


# In[61]:


texts = reviews['Text']
Y = reviews['HelpfulnessNumerator'] / reviews['HelpfulnessDenominator']
Y = Y.map(lambda x: 0 if x < 0.33 else (1 if x < 0.67 else 2))

Y_distr = np.array([len(Y.loc[Y == 0]), len(Y.loc[Y == 1]), len(Y.loc[Y == 2])])
print(Y_distr)
print(Y_distr / sum(Y_distr))


# In[19]:


X = texts.map(lambda t: len(t))
X = np.reshape(np.array(X), (-1, 1))
print(X.shape)


# In[20]:


from sklearn.model_selection import train_test_split

X_train, X_test, y_train, y_test = train_test_split(X, Y, test_size=0.2, random_state=0)
print(X_train.shape)
print(X_test.shape)


# In[21]:


from sklearn.ensemble import RandomForestClassifier

model = RandomForestClassifier(n_estimators=10, random_state=0, n_jobs=-1)
model.fit(X_train, y_train)


# In[22]:


print(model.score(X_train, y_train))
print(model.score(X_test, y_test))


# In[25]:


from sklearn.svm import SVC

svm = SVC(gamma='auto')
svm.fit(X_train, y_train)


# In[26]:


print(svm.score(X_train, y_train))
print(svm.score(X_test, y_test))


# In[32]:


X2 = X.reshape((-1,))
print(np.corrcoef(X2, Y))


# In[36]:


import matplotlib.pyplot as plt

plt.figure(figsize=(4, 10))
plt.scatter(Y, X)
plt.show()


# In[41]:


X_0 = texts.loc[Y == 0]
X_1 = texts.loc[Y == 1]
X_2 = texts.loc[Y == 2]


# In[50]:


def length_hist(X, c):
    plt.figure(figsize=(12, 8))
    plt.hist(X.map(lambda t: len(t)), bins=100, range=(0, 5000))
    plt.title('Histogram of Review Char Lengths for Class = %d' % c)
    plt.show()


# In[51]:


length_hist(X_0, 0)


# In[52]:


length_hist(X_1, 1)


# In[53]:


length_hist(X_2, 2)


# In[60]:


# Calculate number of >2000 char reviews in each class
min_length = 2000
counts = [0, 0, 0]
for i in range(len(Y)):
    if len(texts.iloc[i]) >= min_length:
        counts[Y.iloc[i]] += 1
print(counts)
print(np.array(counts) / sum(counts)) # skewed toward class 2


# In[ ]:




