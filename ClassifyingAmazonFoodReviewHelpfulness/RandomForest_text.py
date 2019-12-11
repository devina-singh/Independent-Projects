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


# In[6]:


import matplotlib.pyplot as plt

Y = reviews['HelpfulnessNumerator'] / reviews['HelpfulnessDenominator']

plt.figure(figsize=(8, 8))
plt.hist(Y)
plt.title('Histogram of Review Helpfulness')
plt.xlabel('Helpfulness')
plt.ylabel('Count')
plt.show()


# In[4]:


texts = reviews['Text']
Y = Y.map(lambda x: 0 if x < 0.33 else (1 if x < 0.67 else 2))
print(len(Y.loc[Y == 0]))
print(len(Y.loc[Y == 1]))
print(len(Y.loc[Y == 2]))


# In[5]:


from sklearn.feature_extraction.text import CountVectorizer

vectorizer = CountVectorizer(stop_words='english', min_df=5)
X = vectorizer.fit_transform(texts)
print(X.shape)


# In[6]:


from sklearn.model_selection import train_test_split

X_train, X_test, y_train, y_test = train_test_split(X, Y, test_size=0.2, random_state=0)
print(X_train.shape)
print(X_test.shape)


# In[7]:


from sklearn.ensemble import RandomForestClassifier

model = RandomForestClassifier(n_estimators=100, max_depth=250, random_state=0, n_jobs=-1)
model.fit(X_train, y_train)


# In[7]:


print(model.score(X_train, y_train))
print(model.score(X_test, y_test))


# In[8]:


from sklearn.metrics import precision_recall_fscore_support

precision, recall, f1, support = precision_recall_fscore_support(y_test, model.predict(X_test), average=None, labels=[0, 1, 2])
print('Results in order by class: [0, 1, 2]')
print('Precision:', precision)
print('Recall:', recall)
print('F1:', f1)
print('# Occurrences:', support)


# In[95]:


indices = np.argsort(model.feature_importances_)[::-1]


# In[96]:


# Print the most important words overall
for i in indices[:100]:
    for word, index in vectorizer.vocabulary_.items():
        if index == i:            
            temp = np.zeros(X_train.shape[1])
            temp[index] = 1
            print(word, model.predict([temp])[0])
            break


# In[108]:


# Print the most important words for a certain class
def print_important_features_for_class(c):
    count = 0
    i = 0
    while count < 10 and i < 1000:
        for word, index in vectorizer.vocabulary_.items():
            if index == indices[i]:
                temp = np.zeros(X_train.shape[1])
                temp[index] = 1
                if model.predict([temp])[0] == c:
                    count += 1
                    print(word, i)
                i += 1


# In[109]:


print_important_features_for_class(0)


# In[110]:


print_important_features_for_class(1)


# In[ ]:




