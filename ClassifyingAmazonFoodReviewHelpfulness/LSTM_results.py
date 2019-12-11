#!/usr/bin/env python
# coding: utf-8

# In[2]:


import pickle

history = pickle.load(open('history_lstm_text_epochs15.pickle', 'rb'))
print(history)


# In[11]:


import matplotlib.pyplot as plt

plt.figure(figsize=(8, 8))
plt.plot(history['val_loss'], label='Validation Loss')
plt.plot(history['loss'], label='Training Loss')
plt.legend()
plt.title('Loss during Training (v1)')
plt.xlabel('Epoch')
plt.ylabel('Loss')
plt.show()


# In[12]:


plt.figure(figsize=(8, 8))
plt.plot(history['val_acc'], label='Validation Accuracy')
plt.plot(history['acc'], label='Training Accuracy')
plt.legend()
plt.title('Accuracy during Training (v1)')
plt.xlabel('Epoch')
plt.ylabel('Accuracy')
plt.show()


# In[3]:


history = pickle.load(open('history_lstm2_text_epochs15.pickle', 'rb'))
print(history)


# In[5]:


import matplotlib.pyplot as plt

plt.figure(figsize=(8, 8))
plt.plot(history['val_loss'], label='Validation Loss')
plt.plot(history['loss'], label='Training Loss')
plt.legend()
plt.title('Loss during Training (v2)')
plt.xlabel('Epoch')
plt.ylabel('Loss')
plt.show()


# In[6]:


plt.figure(figsize=(8, 8))
plt.plot(history['val_acc'], label='Validation Accuracy')
plt.plot(history['acc'], label='Training Accuracy')
plt.legend()
plt.title('Accuracy during Training (v2)')
plt.xlabel('Epoch')
plt.ylabel('Accuracy')
plt.show()


# In[ ]:




