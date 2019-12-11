from keras.models import Sequential
from keras.layers import Input, Dense, Embedding, LSTM, Dropout, Add
from keras.initializers import Constant
from LSTM_constants import *

def get_model(word_index_len=MAX_NUM_WORDS, embedding_matrix=None):
  return Sequential([
    Embedding(
      min(MAX_NUM_WORDS, word_index_len) + 1,
      EMBEDDING_DIM,
      input_length=MAX_SEQ_LENGTH,
      embeddings_initializer=Constant(embedding_matrix) if embedding_matrix is not None else None,
    ),
    LSTM(512, return_sequences=True, dropout=0.5),
    LSTM(512),
    Dropout(0.5),
    Dense(1024, activation='tanh'),
    Dropout(0.5),
    Dense(3, activation='softmax'),
  ])
