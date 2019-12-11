import pandas as pd
import numpy as np
import pickle
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
from keras.utils import to_categorical
from keras.optimizers import Adam
from sklearn.model_selection import train_test_split
from LSTM_model import get_model
from LSTM_constants import *


reviews = pd.read_csv('data/reviews.csv')
print('%d reviews total' % len(reviews))

reviews = reviews.loc[reviews['HelpfulnessDenominator'] > 3]
print('%d reviews with helpfulness data' % len(reviews))

texts = reviews['Text']
texts = texts.map(lambda t: t.replace('<br />', '\n'))
Y = reviews['HelpfulnessNumerator'] / reviews['HelpfulnessDenominator']
Y = Y.map(lambda x: 0 if x < 0.33 else (1 if x < 0.67 else 2))


X_train, X_test, y_train, y_test = train_test_split(texts, Y, test_size=0.2, random_state=0)
y_train_categorical = to_categorical(y_train)
y_test_categorical = to_categorical(y_test)

print('--- Processing texts...')
tokenizer = Tokenizer(num_words=MAX_NUM_WORDS)
tokenizer.fit_on_texts(X_train)
print('Found %s unique tokens.' % len(tokenizer.word_index))

seqs_train = pad_sequences(tokenizer.texts_to_sequences(X_train), maxlen=MAX_SEQ_LENGTH)
seqs_test = pad_sequences(tokenizer.texts_to_sequences(X_test), maxlen=MAX_SEQ_LENGTH)


print('--- Building GloVe index...')
glove = {}
with open('glove.6B.%dd.txt' % EMBEDDING_DIM) as f:
  for line in f:
      values = line.split()
      word = values[0]
      coefs = np.asarray(values[1:], dtype='float32')
      glove[word] = coefs
print('Found %s GloVe vectors.' % len(glove))


print('--- Building Embedding Matrix...')
word_index = tokenizer.word_index
num_words = min(MAX_NUM_WORDS, len(word_index)) + 1
embedding_matrix = np.zeros((num_words, EMBEDDING_DIM))
for word, i in word_index.items():
    if i > MAX_NUM_WORDS:
        continue
    glove_vector = glove.get(word)
    if glove_vector is not None:
        embedding_matrix[i] = glove_vector


model = get_model(word_index_len=len(word_index), embedding_matrix=embedding_matrix)
model.compile(Adam(lr=1e-4), loss='categorical_crossentropy', metrics=['accuracy'])

history = model.fit(
  seqs_train,
  y_train_categorical,
  epochs=15,
  validation_data=(seqs_test, y_test_categorical),
)

pickle.dump(history.history, open('history_lstm2_text_epochs15.pickle', 'wb'))
model.save_weights('lstm2_text_epochs15.h5')

print(model.evaluate(seqs_test, y_test_categorical))
